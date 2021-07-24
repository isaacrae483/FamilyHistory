package Services;

import java.util.Random;

import DAO.AuthDAO;
import DAO.UserDAO;
import Model.User;
import Requests.LoginRequest;
import Result.LoginResponse;
import Result.Response;
import Result.Error;

/**
 * loginService
 */

public class LoginService {
    /**
     * login
     * @return response fail/succeed
     */
    public Response login(LoginRequest request){
        boolean success;
        String token = createAuthToken();
        UserDAO userDao = new UserDAO();
        AuthDAO authDao = new AuthDAO();
        userDao.openConnection();
        //checks to make sure password and user match
        String id = userDao.verify(request.getUserName(), request.getPassword());
        if (id == null){
            userDao.closeConnection(true);
            return new Error("Invalid username or password");
        }

        User user = userDao.getUser(id);
        if(user == null){
            userDao.closeConnection(true);
            return new Error("User does not exist");
        }
        userDao.closeConnection(true);
        authDao.openConnection();
        //makes new authtoken for user
        success = authDao.setAuthToken(id, token);
        if(!success){
            authDao.closeConnection(false);
            return new Error("Could not set AuthToken");
        }
        authDao.closeConnection(true);


        return new LoginResponse(token, user.getUsername(), user.getId());
    }
    //creates random 8 character string
    private String createAuthToken(){
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 8; i++){
            sb.append((char)(random.nextInt(25) + 'a'));
        }

        return sb.toString();
    }
}
