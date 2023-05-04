package com.springboot.couchbase.springbootrealworld.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;

@Configuration
@EnableCouchbaseRepositories(basePackages={"com.springboot.couchbase.springbootrealworld"})
public class CouchbaseConfig extends AbstractCouchbaseConfiguration {

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