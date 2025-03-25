# template-common
To handling all common classes usage inside microservices and common processing

## Introduction:
In the common project, we will put shared classes and processes in the application workflow.

Each common project has different methods and classes, but in this common, we will have these features 
but will be updated and add other features every day.
### Common component
- **Aspect component:**
  - MockApiAspect: to mock controller response if is the controller not ready yet.
  - LogAspect: to log each request and response from and to client side.
- **constant:**
  - SecurityConstant: to set all common constant variables to will be used between microservice.
  - WhiteListUrl: to set URLs don't need token.
- **controller:**
  - endpoint: this package will have classes will name like microservice names to put all URLs her own inside the microservice.
  - ApplicationRole: to put all roles will use in the system.
- **dto.serviceName:** to put any class use outside same microservice
- **exception:** to handle all the exceptions that will happen in the system.
- **interceptor:** to handle requests and extract user information.
- **util:** to put all the helper methods use inside microservices.

> updated always. 