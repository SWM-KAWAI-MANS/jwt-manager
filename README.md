# jwt-manager

![jwt-manager](https://socialify.git.ci/SWM-KAWAI-MANS/jwt-manager/image?description=1&descriptionEditable=&font=Raleway&language=1&name=1&owner=1&pattern=Floating%20Cogs&stargazers=1&theme=Light)

### Description
jwt-manager is a library that simplifies the generation of JWT access tokens and refresh tokens. It provides an easy way to manage JWT token generation and parsing within your applications. By utilizing jwt-manager, you can enhance the security of your token-based authentication system by registering JWT secret keys. Additionally, you have the flexibility to set token expiration times to meet your specific requirements.

### Features
- Simplifies the generation of JWT access tokens and refresh tokens.
- Enhances security by allowing the registration of JWT secret keys.
- Enables customization of token expiration times.

### Getting Started
To get started with jwt-manager, follow these steps:

1. Add the jitpack.io repository to your project's build.gradle file:
```gradle
repositories {
    ...
    maven { url 'https://jitpack.io' }
}
```

2. Add the jwt-manager dependency to your app-level build.gradle file:
```
dependencies {
	      implementation 'com.github.SWM-KAWAI-MANS:jwt-manager:1.0.0'
}
```

3. Register the following environment variables before using jwt-manager:

```
- jwt.access-secret-key
- jwt.access-expire-second
- jwt.refresh-secret-key
- jwt.refresh-expire-second
```

### JwtGenerator
The JwtGenerator interface provides the following methods:

- **JwtToken generate(String id)**

  Generates a JWT token based on the provided id. This method returns a JwtToken object containing the access token and refresh token.

- **String generateAccessToken(String refreshToken)**

  Generates a new access token based on the provided refreshToken. This method returns the newly generated access token as a String.

### JwtExtractor
The JwtExtractor interface provides the following method:

- **JwtPayload extract(String accessToken)**
  
  Extracts the payload from the provided accessToken and returns it as a JwtPayload object.

Using the JwtGenerator and JwtExtractor interfaces, you can simplify the process of JWT token generation and extraction in your application, providing a more secure and efficient way to handle authentication and authorization.
