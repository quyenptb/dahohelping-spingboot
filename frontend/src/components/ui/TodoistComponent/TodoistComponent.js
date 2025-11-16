import React, { useEffect, useState } from 'react';
import axios from 'axios';

const TodoistComponent = () => {
  const [tasks, setTasks] = useState([]);

  useEffect(() => {
    const fetchTasks = async () => {
      try {
        const apiKey = 'f739b2de9cd144a303f5c71dbf589f2dd992c881';
        const url = 'https://api.todoist.com/rest/v1/tasks';

        const response = await axios.get(url, {
          headers: {
            Authorization: `Bearer ${apiKey}`,
          },
        });

        setTasks(response.data);
      } catch (error) {
        console.error('Lỗi khi lấy danh sách công việc:', error);
      }
    };

    fetchTasks();
  }, []);

  return (
    <div>
      <h1>Danh sách công việc</h1>
      <ul>
        {tasks.map((task) => (
          <li key={task.id}>{task.content}</li>
        ))}
      </ul>
    </div>
  );
};

export default TodoistComponent;