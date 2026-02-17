import './App.css'
import ListTodoComponent from './components/ListTodoComponent'
import HeaderComponent from './components/HeaderComponent'
import FooterComponent from './components/FooterComponent'
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom'
import TodoComponent from './components/TodoComponent'
import RegisterComponent from './components/RegisterComponent'
import LoginComponent from './components/LoginComponent'
import { isUserLoggedIn } from './services/AuthService'


function App() {


  function AuthenticatedRoute({ children }) {

    // This function is used to Authenticate the routes

    const isAuth = isUserLoggedIn();

    if (isAuth) {
      return children;
    }

    return <Navigate to="/" /> // If user is not logged in send him to the / path i.e -> LoginComponent
  }



  return (
    <>

      <BrowserRouter>

        <HeaderComponent />

        <Routes>

          {/* http://localhost:8080/ */}
          <Route path='/' element={<LoginComponent />}></Route>

          {/* http://localhost:8080/todos */}
          <Route path='/todos' element={

            <AuthenticatedRoute>
              <ListTodoComponent /> {/* Becomes a children for this Authenticated route*/}
            </AuthenticatedRoute>

          }></Route>

          {/* http://localhost:8080/add-todo */}
          <Route path='/add-todo' element={

            <AuthenticatedRoute>
              <TodoComponent />
            </AuthenticatedRoute>

          }></Route>

          {/* http://localhost:8080/update-todo/1 */}
          <Route path='/update-todo/:id' element={

            <AuthenticatedRoute>
              <TodoComponent />
            </AuthenticatedRoute>

          }></Route> {/* We are using same componenet for update as well as add todo */}

          {/* http://localhost:8080/register */}
          <Route path='/register' element={<RegisterComponent />}></Route>

          {/* http://localhost:8080/login */}
          <Route path='/login' element={<LoginComponent />}></Route>


        </Routes>



        <FooterComponent />

      </BrowserRouter>

    </>
  )
}

export default App
