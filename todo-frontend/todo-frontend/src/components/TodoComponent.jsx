import React, { useEffect } from 'react'
import { useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import { getTodoById, saveTodo, updateTodo } from '../services/TodoService'


const TodoComponent = () => {


  const [title, setTitle] = useState('')
  const [description, setDescription] = useState('')
  const [completed, setCompleted] = useState(false)

  const navigator = useNavigate();
  const { id } = useParams();  // Extracting 



  useEffect(() => {

    if (id) {
      getTodoById(id).then((response) => {
        console.log(response.data);
        setTitle(response.data.title);
        setDescription(response.data.description);
        setCompleted(response.data.completed);
      }).catch(error => {
        console.log(error);
      })
    }

  }, [id]);




  function saveOrUpdate(e) {
    e.preventDefault();

    const todo = { title, description, completed };
    console.log(todo);

    if (id) {
      updateTodo(id, todo).then((response) => {
        console.log(response.data);
        navigator('/todos');

      }).catch(error => {
        console.error(error);
      })
    }
    else {
      saveTodo(todo).then((response) => {
        console.log(response.data)
        navigator('/todos')
      }).catch(error => {
        console.error(error);
      })
    }

  }

  function pageTitle() {
    if (id) {
      return <h2 className='text-center'>Update Todo</h2>
    }
    else {
      return <h2 className='text-center'>Add Todo</h2>
    }
  }




  return (
    <div className='container'>
      <br /><br />
      <div className='row'>
        <div className='card col-md-6 offset-md-3 offset-md-3'>
          {
            pageTitle()
          }
          <div className='card-body' >
            <form >
              <div className='form-group mb-2'>
                <label className='form-label'>Todo Title:</label>
                <input
                  className='form-control'
                  type='text'
                  placeholder='Enter Todo Title'
                  name='title'
                  value={title}
                  onChange={(e) => setTitle(e.target.value)}

                > </input>
              </div>

              <div className='form-group mb-2'>
                <label className='form-label'>Todo Description:</label>
                <input
                  className='form-control'
                  type='text'
                  placeholder='Enter Todo Description'
                  name='description'
                  value={description}
                  onChange={(e) => setDescription(e.target.value)}
                > </input>
              </div>

              <div className='form-group mb-2'>
                <label className='form-label'>Todo Completed:</label>
                <select
                  className='form-control'
                  value={completed}
                  onChange={(e) => setCompleted(e.target.value)}

                >
                  <option value="false">No</option>
                  <option value="true">Yes</option>
                </select>
              </div>

              <button className='btn btn-success' onClick={(e) => saveOrUpdate(e)}>Submit</button>
            </form>
          </div>

        </div>
      </div>



    </div>
  )
}

export default TodoComponent