import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import API_BASE_URL from './api';

function ProviderDashboardScene() {
    const navigate = useNavigate();

    const [providerName, setProviderName] = useState('');
    const [availableUsers, setAvailableUsers] = useState([]);

    const [amount, setAmount] = useState('');
    const [dueDate, setDueDate] = useState('');
    const [targetUser, setTargetUser] = useState('');
    const [noticeMsg, setNoticeMsg] = useState('');

    useEffect(() => {
        const loggedInUser = localStorage.getItem('loggedInUser');
        if (!loggedInUser) {
            navigate('/');
        } else {
            setProviderName(loggedInUser);
        }

        // Updated to use dynamic API_BASE_URL
        axios.get(`${API_BASE_URL}/api/auth/users`)
            .then(res => {
                const customers = res.data.filter(u => u.role === 'USER');
                setAvailableUsers(customers);
            })
            .catch(err => console.error("Could not fetch users:", err));

    }, [navigate]);

    const handleIssueBill = () => {
        if (!targetUser.trim()) {
            alert("Please enter a username for the customer.");
            return;
        }

        const newBill = {
            amount: parseFloat(amount),
            dueDate: dueDate,
            paid: false,
            userUsername: targetUser,
            provider: providerName
        };

        // Updated to use dynamic API_BASE_URL
        axios.post(`${API_BASE_URL}/api/bills`, newBill)
            .then(() => {
                alert("Bill Issued Successfully!");
                setAmount('');
                setDueDate('');
                setTargetUser('');
            })
            .catch(err => alert("Error issuing bill: " + err.message));
    };

    const handleIssueNotice = () => {
        const newNotice = {
            provider: providerName,
            message: noticeMsg
        };

        // Updated to use dynamic API_BASE_URL
        axios.post(`${API_BASE_URL}/api/notices`, newNotice)
            .then(() => {
                alert("Notice Posted Successfully!");
                setNoticeMsg('');
            })
            .catch(err => alert("Error posting notice: " + err.message));
    };

    const handleDeleteAccount = () => {
        const confirmDelete = window.confirm("Are you sure? This will delete your PROVIDER account.");
        if (confirmDelete) {
            // Updated to use dynamic API_BASE_URL
            axios.delete(`${API_BASE_URL}/api/auth/${providerName}`)
                .then(() => {
                    alert("Provider Account Deleted.");
                    localStorage.clear();
                    navigate('/');
                })
                .catch(err => alert("Error deleting account: " + err.message));
        }
    };

    return (
        <div style={{ maxWidth: '600px', margin: '40px auto', padding: '20px', border: '1px solid #ccc', borderRadius: '10px' }}>
            <h2>Provider Portal: <span style={{color: 'blue'}}>{providerName}</span></h2>

            <hr />

            <h3>Issue New Bill</h3>
            <div style={{ display: 'flex', flexDirection: 'column', gap: '10px' }}>

                <label style={{fontSize: '0.9em', color: '#555'}}>Customer Username:</label>
                <input
                    list="customer-options"
                    type="text"
                    placeholder="Type or Double Click to Select User Name..."
                    value={targetUser}
                    onChange={e => setTargetUser(e.target.value)}
                    style={{ padding: '8px' }}
                />

                <datalist id="customer-options">
                    {availableUsers.map(user => (
                        <option key={user.id} value={user.username} />
                    ))}
                </datalist>

                <input
                    type="number"
                    placeholder="Amount ($)"
                    value={amount}
                    onChange={e => setAmount(e.target.value)}
                    style={{ padding: '8px' }}
                />
                <input
                    type="date"
                    value={dueDate}
                    onChange={e => setDueDate(e.target.value)}
                    style={{ padding: '8px' }}
                />
                <button onClick={handleIssueBill} style={{ backgroundColor: '#28a745', color: 'white', padding: '10px', border: 'none', cursor: 'pointer' }}>
                    Send Bill
                </button>
            </div>

            <hr style={{ margin: '30px 0' }} />

            <h3>Issue Service Notice</h3>
            <div style={{ display: 'flex', flexDirection: 'column', gap: '10px' }}>
                <textarea
                    placeholder="e.g. Power outage scheduled..."
                    value={noticeMsg}
                    onChange={e => setNoticeMsg(e.target.value)}
                    style={{ padding: '8px', height: '80px' }}
                />
                <button onClick={handleIssueNotice} style={{ backgroundColor: '#ffc107', color: 'black', padding: '10px', border: 'none', cursor: 'pointer' }}>
                    Post Notice
                </button>
            </div>

            <button onClick={() => { localStorage.clear(); navigate('/'); }} style={{ marginTop: '20px', background: 'none', border: 'none', color: 'blue', cursor: 'pointer', textDecoration: 'underline' }}>
                Log Out
            </button>

            <div style={{ marginTop: '40px', borderTop: '1px solid #eee', paddingTop: '20px', textAlign: 'center' }}>
                <button
                    onClick={handleDeleteAccount}
                    style={{ padding: '10px 20px', backgroundColor: '#dc3545', color: 'white', border: 'none', borderRadius: '5px', cursor: 'pointer' }}
                >
                    Delete Provider Account
                </button>
            </div>
        </div>
    );
}

export default ProviderDashboardScene;