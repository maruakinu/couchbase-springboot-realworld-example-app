# Couchbase Springboot Realworld Example app
Realworld is a backend project using springboot that uses Couchbase for its Database. Couchbase is a NoSQL distributed document database (JSON) with many of the best features of a relational DBMS: SQL, distributed ACID transactions, and much more. For this project we included a frontend in order to visualize you the full stack development and how Real World works


## Getting Started

### Couchbase
Couchbase offers different deployment options such as:
- Couchbase Capella: a fully-managed Database as a Service (DBaaS)
- Public Cloud: Amazon Web Services(AWS), Google Cloud Platform (GCP), Microsoft Azure, etc.
- On-Premise: Local Machine Download
- Virtual Machine/Container: Docker

Here is the link to have access to Couchbase, make sure to register and understand how these different deployments works. Couchbase has a lot of documentation that will help you all through out.

Here is the link for you to access Couchbase ` https://www.couchbase.com/downloads/get-started/ `


### BackEnd
To get the backend running locally, clone this repo ` git clone https://github.com/maruakinu/couchbase-springboot-realworld-example-app.git `
and make sure your Couchbase Capella or Enterprise Edition/docker is running.

For this project we are using Couchbase on a docker container which also works on a Couchbase Enterprise Edition. If you want to connect to your Couchbase Capella, comment this code in MyCouchbaseConfig

```
    public String getConnectionString() {
        return "couchbase://127.0.0.1";
    }

    ;

    public String getUserName() {
        return "Administrator";
    }

    public String getPassword() {
        return "123456";
    }

    ;

    public String getBucketName() {
        return "realworld";
    }
```
Then uncomment this connection for the Couchbase Capella to work. This code may differ since this is my configuration to my own account in Couchbase Capella.

```
    public String getConnectionString() {

      return "couchbases://cb.wxif3r-rblv1zaqs.cloud.couchbase.com";
    };

        public String getUserName() {
        return "Administrator";
    }

    public String getPassword() {
        return "123456,";
    };

    public String getBucketName() {
        return "realworld";
    };

    @Override
    protected void configureEnvironment(final ClusterEnvironment.Builder builder) {
        builder.securityConfig().enableTls(true);
        builder.ioConfig(IoConfig.enableDnsSrv(true)).build();
    }
```

 once you are done, find the CouchbaseProjectApplication.java and start running your backend from the IDE you are using.
 
 ## application.properties
 
 Make sure to change your application.properties depending on the Bucket and Account you have made in your Couchbase, this also works on Couchbase Capella.
`spring.couchbase.bucket.name=realworld
spring.couchbase.bucket.user=Administrator
spring.couchbase.bucket.password=123456`
 
 ## Connecting your Scope and Collection
 
 In order for you to connect through your Couchbase Bucket, make sure to change your scope and collection in every repository depending on the Scope and Collection you created in Couchbase. 
 Here is an example of the code from the repository that you will be changing: 
`@Scope("sample")
@Collection("article")`

 
## Functionality overview

Real World application is a social blogging site just like the social platform Medium.com clone. It uses a custom API for all requests, including authentication. For testing purposes on how the API works, you can visit ` https://api.realworld.io/api-docs/#/ ` before testing out the full stack development. You can also use Postman for the API testing in the backend before running the frontend.

**General functionality:**

- Authenticate users via JWT (login/signup pages + logout button on settings page)
- CRUD users (sign up & settings page - no deleting required)
- CRUD Articles
- CRUD Comments on articles (no updating required)
- GET and display paginated lists of articles
- Favorite articles
- Follow other users

