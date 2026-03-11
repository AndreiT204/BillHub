import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function AdminDashboardScene() {
    const navigate = useNavigate();
    const [users, setUsers] = useState([]);

    const [viewMode, setViewMode] = useState('LIST');
    const [selectedEntity, setSelectedEntity] = useState(null);
    const [entityItems, setEntityItems] = useState([]);

    useEffect(() => {
        fetchAllUsers();
    }, []);

    const fetchAllUsers = () => {
        axios.get('http://localhost:8080/api/auth/users')
            .then(res => setUsers(res.data))
            .catch(err => console.error(err));
    };

    const deleteAccount = (username) => {
        if(window.confirm(`Delete account: ${username}?`)) {
            axios.delete(`http://localhost:8080/api/auth/${username}`)
                .then(() => fetchAllUsers())
                .catch(err => alert("Error: " + err.message));
        }
    };

    const handleUserClick = (user) => {
        setSelectedEntity(user);
        setViewMode('USER_BILLS');
        axios.get(`http://localhost:8080/api/bills/${user.username}`)
            .then(res => setEntityItems(res.data))
            .catch(err => alert("Could not fetch bills"));
    };

    const deleteBill = (id) => {
        axios.delete(`http://localhost:8080/api/bills/${id}`)
            .then(() => handleUserClick(selectedEntity)) // Refresh list
            .catch(err => alert("Error deleting bill"));
    };

    const handleProviderClick = (user) => {
        setSelectedEntity(user);
        setViewMode('PROVIDER_NOTICES');
        axios.get(`http://localhost:8080/api/notices/${user.username}`)
            .then(res => setEntityItems(res.data))
            .catch(err => alert("Could not fetch notices"));
    };

    const deleteNotice = (id) => {
        axios.delete(`http://localhost:8080/api/notices/${id}`)
            .then(() => handleProviderClick(selectedEntity)) // Refresh list
            .catch(err => alert("Error deleting notice"));
    };

    const renderMainList = () => {
        const standardUsers = users.filter(u => u.role !== 'PROVIDER' && u.role !== 'ADMIN');
        const providers = users.filter(u => u.role === 'PROVIDER');

        return (
            <div style={{ display: 'flex', gap: '20px' }}>
                <div style={{ flex: 1, border: '1px solid #ccc', padding: '10px' }}>
                    <h3>Users</h3>
                    {standardUsers.map(u => (
                        <div key={u.id} style={{ borderBottom: '1px solid #eee', padding: '10px', display: 'flex', justifyContent: 'space-between' }}>
                            <div>
                                <strong
                                    style={{ color: 'blue', cursor: 'pointer', textDecoration: 'underline' }}
                                    onClick={() => handleUserClick(u)}
                                >
                                    {u.username}
                                </strong>
                            </div>
                            <button onClick={() => deleteAccount(u.username)} style={{background:'red', color:'white', border:'none', cursor:'pointer'}}>X</button>
                        </div>
                    ))}
                </div>

                <div style={{ flex: 1, border: '1px solid #ccc', padding: '10px' }}>
                    <h3>Providers</h3>
                    {providers.map(u => (
                        <div key={u.id} style={{ borderBottom: '1px solid #eee', padding: '10px', display: 'flex', justifyContent: 'space-between' }}>
                            <div>
                                <strong
                                    style={{ color: 'green', cursor: 'pointer', textDecoration: 'underline' }}
                                    onClick={() => handleProviderClick(u)}
                                >
                                    {u.username}
                                </strong>
                            </div>
                            <button onClick={() => deleteAccount(u.username)} style={{background:'red', color:'white', border:'none', cursor:'pointer'}}>X</button>
                        </div>
                    ))}
                </div>
            </div>
        );
    };

    const renderDetailView = () => (
        <div>
            <button onClick={() => setViewMode('LIST')} style={{marginBottom: '10px'}}>← Back to List</button>
            <h3>
                Manage {viewMode === 'USER_BILLS' ? 'Bills for User: ' : 'Notices for Provider: '}
                <span style={{color:'blue'}}>{selectedEntity.username}</span>
            </h3>

            <table border="1" cellPadding="10" style={{width: '100%', borderCollapse: 'collapse'}}>
                <thead>
                <tr style={{background: '#f0f0f0'}}>
                    <th>ID</th>
                    <th>{viewMode === 'USER_BILLS' ? 'Amount / Due' : 'Message'}</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                {entityItems.map(item => (
                    <tr key={item.id}>
                        <td>{item.id}</td>
                        <td>
                            {viewMode === 'USER_BILLS'
                                ? `$${item.amount} (Due: ${item.dueDate})`
                                : item.message
                            }
                        </td>
                        <td>
                            <button
                                style={{background:'red', color:'white', border:'none', padding:'5px 10px', cursor:'pointer'}}
                                onClick={() => viewMode === 'USER_BILLS' ? deleteBill(item.id) : deleteNotice(item.id)}
                            >
                                Delete
                            </button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );

    return (
        <div style={{ padding: '20px', fontFamily: 'Arial' }}>
            <div style={{display:'flex', justifyContent:'space-between', marginBottom:'20px'}}>
                <h2>Super Admin Dashboard</h2>
                <button onClick={() => navigate('/')}>Log Out</button>
            </div>
            {viewMode === 'LIST' ? renderMainList() : renderDetailView()}
        </div>
    );
}

export default AdminDashboardScene;