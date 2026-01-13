import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function LoginScene() {
    const navigate = useNavigate();

    //Toggle between Login and Register
    const [isRegistering, setIsRegistering] = useState(false);

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [role, setRole] = useState('USER'); // Default to Standard User

    const handleAction = () => {
        if (!username || !password) {
            alert("Please enter both username and password.");
            return;
        }

        const userData = { username, password, role };

        if (isRegistering) {
            axios.post('http://localhost:8080/api/auth/register', userData)
                .then(() => {
                    alert("Registration Successful! Please Log In.");
                    setIsRegistering(false);
                })
                .catch(err => alert("Registration Failed: " + (err.response?.data || "Error")));
        } else {
            axios.post('http://localhost:8080/api/auth/login', userData)
                .then(response => {
                    const user = response.data;

                    localStorage.setItem('loggedInUser', user.username);

                    if (user.role === 'ADMIN') {
                        navigate('/admin');
                    } else if (user.role === 'PROVIDER') {
                        navigate('/provider');
                    } else {
                        navigate('/dashboard');
                    }
                })
                .catch(() => alert("Invalid Username or Password"));
        }
    };

    return (
        <div style={{ textAlign: 'center', marginTop: '50px', fontFamily: 'Arial, sans-serif' }}>
            <h2>{isRegistering ? "Create Account" : "Welcome Back"}</h2>

            <div style={{
                border: '1px solid #ccc',
                padding: '30px',
                display: 'inline-block',
                borderRadius: '10px',
                boxShadow: '0 4px 8px rgba(0,0,0,0.1)',
                backgroundColor: 'white',
                width: '300px'
            }}>

                <input
                    type="text"
                    placeholder="Username"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    style={{ width: '90%', padding: '10px', marginBottom: '10px', borderRadius: '5px', border: '1px solid #ddd' }}
                />

                <input
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    style={{ width: '90%', padding: '10px', marginBottom: '10px', borderRadius: '5px', border: '1px solid #ddd' }}
                />

                {isRegistering && (
                    <div style={{ marginBottom: '15px', textAlign: 'left', marginLeft: '10px' }}>
                        <label>I am a: </label>
                        <select value={role} onChange={(e) => setRole(e.target.value)} style={{ padding: '5px', marginLeft: '10px' }}>
                            <option value="USER">Standard User</option>
                            <option value="PROVIDER">Utility Provider</option>
                        </select>
                    </div>
                )}

                <button
                    onClick={handleAction}
                    style={{
                        width: '100%',
                        padding: '10px',
                        backgroundColor: isRegistering ? '#28a745' : '#007bff',
                        color: 'white',
                        border: 'none',
                        borderRadius: '5px',
                        cursor: 'pointer',
                        fontWeight: 'bold',
                        marginTop: '10px'
                    }}
                >
                    {isRegistering ? "Sign Up" : "Login"}
                </button>

                <p style={{ marginTop: '20px', fontSize: '0.9em', color: '#666' }}>
                    {isRegistering ? "Already have an account?" : "New to BillHub?"}
                    <br/>
                    <span
                        onClick={() => setIsRegistering(!isRegistering)}
                        style={{ color: 'blue', textDecoration: 'underline', cursor: 'pointer', fontWeight: 'bold' }}
                    >
            {isRegistering ? "Login here" : "Create an account"}
          </span>
                </p>

            </div>
        </div>
    );
}

export default LoginScene;