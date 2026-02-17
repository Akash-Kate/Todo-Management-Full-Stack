import React, { useEffect, useState } from 'react'
import { completeTodo, deleteTodo, getAllTodos, inCompleteTodo } from '../services/TodoService';

import { useNavigate } from 'react-router-dom';
import { isAdmin } from '../services/AuthService';

const ListTodoComponent = () => {


    const navigator = useNavigate();

    const [todos, setTodos] = useState([]);

    const isadmin = isAdmin();

    console.log("Console admin -->",isadmin);

    useEffect(() => {
        listTodos();
    }, [])


    function listTodos() {
        getAllTodos().then((response) => {
            setTodos(response.data);
        }).catch(error => {
            console.error(error);
        })
    }

    function addNewTodo() {
        navigator("/add-todo");
    }


    function updateTodo(id) {
        console.log("Check id for update todo ->", id);
        navigator(`/update-todo/${id}`); // Using back ticks
    }

    function removeTodo(id) {
        deleteTodo(id).then((response) => {
            listTodos();
        }).catch(error => {
            console.error(error)
        })
    }


    function markCompleteTodo(id) {
        completeTodo(id).then((response) => {
            listTodos();
        }).catch(error => {
            console.error(error);
        })
    }

    function markInCompleteTodo(id) {
        inCompleteTodo(id).then((response) => {
            listTodos();
        }).catch(error => {
            console.error(error);
        })
    }

    return (
        <div className='container'>

            <h2 className='text-center'>List of Todos</h2>
            {
                isadmin && <button className='btn btn-primary mb-2' onClick={addNewTodo}>Add Todo</button>
            }

            <div>
                <table className='table table-bordered table-striped'>
                    <thead>
                        <tr>
                            <th>Todo Title</th>
                            <th>Tod Description</th>
                            <th>Tod Completed</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            todos.map(todo =>
                                <tr key={todo.id}>
                                    <td>{todo.title}</td>
                                    <td>{todo.description}</td>
                                    <td>{todo.completed ? 'YES' : 'NO'}</td>
                                    <td>
                                        {
                                            isadmin && <button className='btn btn-info' onClick={() => updateTodo(todo.id)}>Update</button>
                                        }
                                        {
                                            isadmin && <button className='btn btn-danger' onClick={() => removeTodo(todo.id)} style={{ marginLeft: "10px" }}>Delete</button>
                                        }
                                        
                                        <button className='btn btn-success' onClick={() => markCompleteTodo(todo.id)} style={{ marginLeft: "10px" }}>Complete</button>
                                        <button className='btn btn-warning' onClick={() => markInCompleteTodo(todo.id)} style={{ marginLeft: "10px" }}>In Complete</button>

                                    </td>

                                </tr>
                            )
                        }

                    </tbody>
                </table>

            </div>

        </div>
    )
}

export default ListTodoComponent