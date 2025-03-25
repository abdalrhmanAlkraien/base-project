# template-parent-project
To put common dependencies between microservice.

## Introduction
In the parent project, we will put shared dependencies that will use inside microservices.

***
## what are the dependencies in the parent project?
> I will write artifactId below to easily search for dependencies included.
- ```spring-boot-starter-web```
- ```spring-boot-starter-test```
- ```spring-cloud-starter-sleuth```
- ```spring-boot-starter-data-jdbc```
- ```spring-boot-starter-data-jpa```
- ```spring-cloud-starter-netflix-eureka-client```
- ```spring-boot-starter-actuator```
- ```spring-boot-devtools```
- ```h2```
- ```postgresql```
- ```lombok```
- ```spring-boot-starter-validation```
- ```spring-cloud-starter-openfeign```
- ```springdoc-openapi-ui```
- ```modelmapper```
- ```java-jwt```
- ```jjwt-api```
- ```jjwt-impl```
- ```jjwt-jackson```
- ```commons-io```

## what are the dependency Management in the parent project ?
> I will write artifactId below to easily search for dependencies included.
- ```spring-cloud-dependencies```

## what is the plugin included in the parent project?
> I will write artifactId below to easily search for dependencies included.
- ```spring-boot-maven-plugin```
- ```maven-compiler-plugin```

## what is the repository included in the parent project?
> I will write name of the repositories below to easily search for dependencies included.
- ```Spring Milestones```
- ```Spring Snapshots```

## what is the repository plugin included in the parent project?
> I will write name of the repositories below to easily search for dependencies included.
- ```Spring Milestones```
- ```Spring Snapshots```

## what is the profiles included in the parent project?
> I will write id of the repositories below to easily search for dependencies included.
1. [x] ```dev```
2. [ ] ```local```
3. [ ] ```prod```


## our parent project structures
> You must commit to the following structure to provide easy maintenance In the future.

- **we will put common dependencies here.**
- **the dependency version put as a variable in the right place.**
- **don't put any other thing before ask Abdulrahman or Omar.**