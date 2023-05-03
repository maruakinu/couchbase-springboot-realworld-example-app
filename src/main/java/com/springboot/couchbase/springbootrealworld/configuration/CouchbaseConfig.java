package com.springboot.couchbase.springbootrealworld.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;

@Configuration
@EnableCouchbaseRepositories(basePackages={"com.springboot.couchbase.springbootrealworld"})
public class CouchbaseConfig extends AbstractCouchbaseConfiguration {

//    @Override
//    protected void configureEnvironment(final ClusterEnvironment.Builder builder) {
//        builder.securityConfig().enableTls(true);
//                builder.ioConfig(IoConfig.enableDnsSrv(true)).build();
//    }

//    ClusterEnvironment env = ClusterEnvironment.builder()
//            .securityConfig(SecurityConfig.enableTls(true)/*.trustManagerFactory(InsecureTrustManagerFactory.INSTANCE)*/)
//            .ioConfig(IoConfig.enableDnsSrv(true)).build();

//    public String getConnectionString() {
//        return "cb.yy8kujgkkjbscew4.cloud.couchbase.com";
//    };

    public String getConnectionString() {
        return "couchbase://127.0.0.1";
    };

    public String getUserName() {
        return "Administrator";
    }

    public String getPassword() {
        return "Maru@Akinu11";
    };

    public String getBucketName() {
        return "sample";
    };





}