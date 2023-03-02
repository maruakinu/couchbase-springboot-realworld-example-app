package com.springboot.couchbase.springbootrealworld.domain.user.service;

import com.springboot.couchbase.springbootrealworld.domain.user.dto.UserDto;
import com.springboot.couchbase.springbootrealworld.domain.user.entity.UserDocument;
import com.springboot.couchbase.springbootrealworld.domain.user.repository.UserRepository;
import com.springboot.couchbase.springbootrealworld.exception.AppException;
import com.springboot.couchbase.springbootrealworld.exception.Error;
import com.springboot.couchbase.springbootrealworld.security.AuthUserDetails;
import com.springboot.couchbase.springbootrealworld.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@RequiredArgsConstructor
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class UserServiceImpl implements UserService {
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtils jwtUtils;


//    @Override
//    public UserDto registration(final UserDto.Registration registration) {
//  //      JwtUtils u = new JwtUtils("String", 1L);
//        userRepository.findByUsernameOrEmail(registration.getUsername(), registration.getEmail()).stream().findAny().ifPresent(entity -> {throw new AppException(Error.DUPLICATED_USER);});
//        UserDocument userDocument = UserDocument.builder().username(registration.getUsername()).email(registration.getEmail()).build();
//        userRepository.save(userDocument);
//        return UserDto.builder().username(userDocument.getUsername()).bio(userDocument.getBio()).email(userDocument.getEmail()).image(userDocument.getImage()).build();
// //       return UserDto.builder().username(userDocument.getUsername()).bio(userDocument.getBio()).email(userDocument.getEmail()).image(userDocument.getImage()).token(u.encode(userDocument.getEmail())).build();
//    //    return convertEntityToDto(userDocument);
//    }

//    @Override
//    public UserDto registration(UserDto registration) {
//        userRepository.findByUsernameOrEmail(registration.getUsername(), registration.getEmail()).stream().findAny().ifPresent(entity -> {throw new AppException(Error.DUPLICATED_USER);});
//        UserDocument userDocument = UserDocument.builder()
////                .id(registration.getId())
//                .username(registration.getUsername())
//                .email(registration.getEmail())
//                .password(passwordEncoder.encode(registration.getPassword()))
//                .bio("")
//                .build();
//        userRepository.save(userDocument);
//        return convertEntityToDto(userDocument);
//    }

    @Override
    public UserDto registration(final UserDto.Registration registration) {
        userRepository.findByUsernameOrEmail(registration.getUsername(), registration.getEmail()).stream().findAny().ifPresent(entity -> {throw new AppException(Error.DUPLICATED_USER);});
        UserDocument userEntity = UserDocument.builder()
                .username(registration.getUsername())
                .email(registration.getEmail())
                .password(passwordEncoder.encode(registration.getPassword()))
                .bio("")
                .build();
        userRepository.save(userEntity);
        return convertEntityToDto(userEntity);
    }

//    @Override
//    public UserDto registration(final UserDto.Registration registration) {
//        UserDocument userDocument = UserDocument.builder().username(registration.getUsername()).email(registration.getEmail()).password(registration.getPassword()).bio("").build();
//        userRepository.save(userDocument);
//        return convertEntityToDto(userDocument);
//    }

    @Transactional(readOnly = true)
    @Override
    public UserDto login(UserDto.Login login) {
        UserDocument userDocument = userRepository.findByEmail(login.getEmail()).filter(user -> passwordEncoder.matches(login.getPassword(), user.getPassword())).orElseThrow(() -> new AppException(Error.LOGIN_INFO_INVALID));
   //     UserDocument userDocument = userRepository.findByEmail(login.getEmail()).orElseThrow(() -> new AppException(Error.LOGIN_INFO_INVALID));
   //     UserDocument userDocument = userRepository.findByEmail(login.getEmail()).orElseThrow(() -> new AppException(Error.LOGIN_INFO_INVALID));
        System.out.println("Login : " + login.getEmail());
        return convertEntityToDto(userDocument);
    }

    @Transactional(readOnly = true)
    @Override
    public UserDto currentUser(AuthUserDetails authUserDetails) {
        UserDocument userEntity = userRepository.findByEmail(authUserDetails.getEmail()).orElseThrow(() -> new AppException(Error.USER_NOT_FOUND));
        return convertEntityToDto(userEntity);
    }

    @Override
    public UserDto update(UserDto.Update update, AuthUserDetails authUserDetails) {
        UserDocument userDocument = userRepository.findByEmail(authUserDetails.getEmail()).orElseThrow(() -> new AppException(Error.USER_NOT_FOUND));

        if (update.getUsername() != null) {
            userRepository.findByUsername(update.getUsername())
                    .filter(found -> !found.getId().equals(userDocument.getId()))
                    .ifPresent(found -> {throw new AppException(Error.DUPLICATED_USER);});
            userDocument.setUsername(update.getUsername());
        }

        if (update.getEmail() != null) {
            userRepository.findByEmail(update.getEmail())
                    .filter(found -> !found.getId().equals(userDocument.getId()))
                    .ifPresent(found -> {throw new AppException(Error.DUPLICATED_USER);});
            userDocument.setEmail(update.getEmail());
        }


        if (update.getBio() != null) {
            userDocument.setBio(update.getBio());
        }

        if (update.getImage() != null) {
            userDocument.setImage(update.getImage());
        }

        userRepository.save(userDocument);
        return convertEntityToDto(userDocument);
    }

//    @Transactional(readOnly = true)
//    @Override
//    public UserDto login(UserDto.Login login) {
//        UserDocument userDocument = userRepository.findByEmail(login.getEmail()).filter(user -> passwordEncoder.matches(login.getPassword(), user.getPassword())).orElseThrow(() -> new AppException(Error.LOGIN_INFO_INVALID));
//        return convertEntityToDto(userDocument);
//    }

//    @Transactional(readOnly = true)
//    @Override
//    public UserDto currentUser(AuthUserDetails authUserDetails) {
//        UserDocument userDocument = userRepository.findById(authUserDetails.getId()).orElseThrow(() -> new AppException(Error.USER_NOT_FOUND));
//        return convertEntityToDto(userDocument);
//    }


    private UserDto convertEntityToDto(UserDocument userEntity) {
        return UserDto.builder()
                .id(userEntity.getId())
                .password(userEntity.getPassword())
                .username(userEntity.getUsername())
                .bio(userEntity.getBio())
                .email(userEntity.getEmail())
                .image(userEntity.getImage())
                .token(jwtUtils.encode(userEntity.getEmail()))
                .build();
    }

//    private UserDto convertEntityToDtos(UserDocument userEntity) {
//        return UserDto.builder()
//                .username(userEntity.getUsername())
//                .bio(userEntity.getBio())
//                .email(userEntity.getEmail())
//                .image(userEntity.getImage())
//                .token(jwtUtils.encode(userEntity.getEmail()))
//                .build();
//    }

//    private UserDto convertEntityToDto(UserDocument userDocument) {
//        return UserDto.builder()
//                .id(userDocument.getId())
//                .username(userDocument.getUsername())
//                .bio(userDocument.getBio())
//                .email(userDocument.getEmail())
//                .image(userDocument.getImage())
////                .token(jwtUtils.encode(userDocument.getEmail()))
//                .build();
//    }




}
