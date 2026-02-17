import axios from "axios";

const AUTH_REST_API_BASE_URL = 'http://localhost:8080/api/auth';

export const registerAPICall = (registerObj) => axios.post(AUTH_REST_API_BASE_URL + '/register', registerObj);

export const loginRestAPICall = (loginObj) => axios.post(AUTH_REST_API_BASE_URL + '/login', loginObj);

export const storeToken = (token) => localStorage.setItem("token", token);

export const getToken = (token) => localStorage.getItem("token");

export const saveLoggedInUser = (userName, role) => {

    sessionStorage.setItem("authenticatedUser", userName);
    sessionStorage.setItem("role",role);

}

export const isUserLoggedIn = () => {
  
    const userName = sessionStorage.getItem("authenticatedUser");

    if(userName ==  null) 
    return false;
    else
    return true;
    
}

export const getLoggedInUser = () => {

    const userName = sessionStorage.getItem("authenticatedUser");
    return userName;

}

export const logout = () => {
    localStorage.clear();
    sessionStorage.clear();
}


export const isAdmin = () => {

    let role = sessionStorage.getItem("role");
    
    
    if(role != null && role === 'ROLE_ADMIN'){
        return true;
    }else{
        return false;
    }

}