package com.springboot.couchbase.springbootrealworld.configuration;

import com.couchbase.client.core.env.IoConfig;
import com.couchbase.client.core.env.SecurityConfig;
import com.couchbase.client.java.env.ClusterEnvironment;
import com.springboot.couchbase.springbootrealworld.security.JwtUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;

import java.nio.file.Paths;

@Configuration
@EnableCouchbaseRepositories(basePackages={"com.springboot.couchbase.springbootrealworld"})
public class MyCouchbaseConfig extends AbstractCouchbaseConfiguration {

    @Override
    protected void configureEnvironment(final ClusterEnvironment.Builder builder) {
        builder.securityConfig().enableTls(true);
                builder.ioConfig(IoConfig.enableDnsSrv(true)).build();
    }

//    ClusterEnvironment env = ClusterEnvironment.builder()
//            .securityConfig(SecurityConfig.enableTls(true)/*.trustManagerFactory(InsecureTrustManagerFactory.INSTANCE)*/)
//            .ioConfig(IoConfig.enableDnsSrv(true)).build();

    public String getConnectionString() {
        return "couchbases://cb.yy8kujgkkjbscew4.cloud.couchbase.com";
    };

    public String getUserName() {
        return "cbuser";
    }

    public String getPassword() {
        return "Maru@Akinu11";
    };

    public String getBucketName() {
        return "sample";
    };





}