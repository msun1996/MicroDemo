namespace java com.micro.thrift.user

struct UserInfo {
    1:i32 id,
    2:string username,
    3:string password,
    4:string realName,
    5:string mobile,
    6:string email
}

service UserService {

    UserInfo getUserById(1:i32 id);
    UserInfo getUserByName(1:string username);
    void regiserUser(1:UserInfo userInfo);

}