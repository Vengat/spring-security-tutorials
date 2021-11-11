# spring-security-tutorials
We will have multiple spring security projects


Contract

Description

UserDetails

Represents the user as seen by Spring Security.

GrantedAuthority

Defines an action within the purpose of the application that is allowable to the user (for example, read, write, delete, etc.).

UserDetailsService

Represents the object used to retrieve user details by username.

UserDetailsManager

A more particular contract for UserDetailsService. Besides retrieving the user by username, it can also be used to mutate a collection of users or a specific user.

PasswordEncoder

Specifies how the password is encrypted or hashed and how to check if a given encoded string matches a plaintext password.
