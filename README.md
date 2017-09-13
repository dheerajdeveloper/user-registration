# user-registration

# add locaiton
http://localhost:8080/user-registration/location/add

{"userId" :"4",
"latitude":"75.123456",
"longitude":"345.768543",
"locationTime":"23-11-2017T88:00:00Z"
}


# get location details for user
http://localhost:8080/user-registration/location/getLocationForUser?userId=4

# add privilege
http://localhost:8080/user-registration/priviledge/add

{"name" :"READ_PRIVILEGE"
}

# add role
http://localhost:8080/user-registration/role/add

{"name" :"ROLE_USER"
}

# add privilege for role
http://localhost:8080/user-registration/role/addPrivilegeForRole

{"roleName" :"ROLE_ADMIN",
"privilegeName": "READ_PRIVILEGE"
}

# add user
http://localhost:8080/user-registration/user/add

{"email" :"dheeraj2311@gmail.com",
"enabled":"true",
"firstname" :"Dheeraj",
"lastname":"Tyagi",
"password":"paytm123"
}

#add role for user
http://localhost:8080/user-registration/user/addRoleForUser

{"userEmaild" :"dheeraj2311@gmail.com",
"roleName":"ROLE_USER"
}