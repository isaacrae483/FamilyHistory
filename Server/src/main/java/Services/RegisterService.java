package Services;

import java.util.Random;

import DAO.*;
import Data.Fnames;
import Data.Locations;
import Data.Mnames;
import Data.Snames;
import Model.User;
import Result.Error;
import Result.Response;
import Result.RegisterResponse;
import Requests.RegisterRequest;

/**
 *RegisterService
 */

public class RegisterService {
    /**
     * register
     * @return response fail/succeed
     */
    public Response register(RegisterRequest request, Fnames fNames, Mnames mNames, Snames sNames, Locations locs){
        boolean success;
        String token = createAuthToken();
        //create new user
        User user = new User(request.getUserName(), request.getPassword(), request.getEmail(), request.getFirstName(), request.getLastName(), request.getGender(), createId());
        UserDAO userDao = new UserDAO();
        AuthDAO authDao = new AuthDAO();
        userDao.openConnection();
        if(userDao.verify(user.getUsername(), user.getPassword()) != null) {
            userDao.closeConnection(true);
            return new Error("User already registered");
        }

        success = userDao.createUser(user);
        if (!success){
            userDao.closeConnection(false);
            return new Error("Could not register the user");
        }
        userDao.closeConnection(true);
        authDao.openConnection();
        //set Authtoken for user
        success = authDao.setAuthToken(user.getId(), token);
        if (!success){
            authDao.closeConnection(false);
            return new Error("Could not register the user");
        }
        authDao.closeConnection(true);
        FillService fill = new FillService();
        Response response = fill.fill(user.getUsername(), 4, fNames, mNames, sNames, locs);
        if(response instanceof Error){
            return response;
        }
        return new RegisterResponse(token, user.getUsername(), user.getId());
    }
    //generates random 6 digit ID
    private String createId(){
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++){
            sb.append(random.nextInt(9));
        }

        return sb.toString();
    }
    //generates random 8 character String
    private String createAuthToken(){
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 8; i++){
            sb.append((char)(random.nextInt(25) + 'a'));
        }

        return sb.toString();
    }
}
