import React, { useState } from 'react'
import { loginRestAPICall, saveLoggedInUser, storeToken } from '../services/AuthService';
import { useNavigate } from 'react-router-dom';

const LoginComponent = () => {

    const [userNameOrEmail, setUserNameOrEmail] = useState('');
    const [password, setPassword] = useState('');


    const navigator = useNavigate();


    async function handleLoginForm(e) {
        e.preventDefault();

        const loginObj = { userNameOrEmail, password };

        console.log(loginObj);

        await loginRestAPICall(loginObj).then((response) => {
            console.log(response.data);

            // const token = 'Basic ' + window.btoa(userNameOrEmail + ":" + password); // btoa method takes a string and converts it into the Base64 string
            // This is called a Basic Auth Token , We need to pass this basic auth token in todo related REST API'S


            const token = 'Bearer ' + response.data.accessToken;

            const role = response.data.role;


            storeToken(token); // create the token and stored it in the browser local storage

            saveLoggedInUser(userNameOrEmail, role);



            // naviagte to list of todos page
            navigator('/todos');  // This will not work directly as we need to pass the user credentials in Header of this request in order to get al todos
            // Because of spring security restrictions

            window.location.reload(false);  // Whenever the user is logged in this page is displayed but we need to refresh the page in order fot our conditional rendering to work
            
            // You are storing the usernam and token in local storgae and session storage , React does not render its components based on session or local storage
            // React renders of the basis of props and state , so wee need to refresh the page because to reload the componenet and thus the user login logic will work

        }).catch(error => {
            console.error(error);
        });

    }


    return (
        <div className='container'>
            <br /> <br />
            <div className='row'>
                <div className='col-md-6 offset-md-3'>
                    <div className='card'>
                        <div className='card-hearder'>
                            <h2 className='text-center'> User Login Form </h2>
                        </div>
                        <div className='card-body'>
                            <form>

                                <div className='row mb-3'>
                                    <label className='col-md-3 controll-label'> Username or Email </label>
                                    <div className='col-md-9'>
                                        <input
                                            type='text'
                                            name='userNameOrEmail'
                                            className='form-control'
                                            placeholder='Enter userName Or Email'
                                            value={userNameOrEmail}
                                            onChange={(e) => setUserNameOrEmail(e.target.value)}

                                        >
                                        </input>

                                    </div>
                                </div>

                                <div className='row mb-3'>
                                    <label className='col-md-3 controll-label'> Password </label>
                                    <div className='col-md-9'>
                                        <input
                                            type='password'
                                            name='password'
                                            className='form-control'
                                            placeholder='Enter Password'
                                            value={password}
                                            onChange={(e) => setPassword(e.target.value)}

                                        >
                                        </input>

                                    </div>
                                </div>

                                <div className='form-group mb-3'>
                                    <button className='btn btn-primary' onClick={(e) => handleLoginForm(e)}> Submit </button>
                                </div>

                            </form>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    )
}

export default LoginComponent