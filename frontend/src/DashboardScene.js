import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Link, useNavigate } from 'react-router-dom';
import './Dashboard.css';

function DashboardScene() {
    const navigate = useNavigate();

    const [bills, setBills] = useState([]);
    const [currentUser, setCurrentUser] = useState('');

    useEffect(() => {
        const username = localStorage.getItem('loggedInUser');
        if (!username) {
            navigate('/');
            return;
        }
        setCurrentUser(username);
        fetchBills(username);
    }, [navigate]);

    const fetchBills = (username) => {
        axios.get(`http://localhost:8080/api/bills/${username}`)
            .then(response => setBills(response.data))
            .catch(error => console.error("Error fetching bills:", error));
    };

    const handlePay = (billId) => {
        axios.post(`http://localhost:8080/api/bills/${billId}/pay`)
            .then(response => {
                alert("Success! Bill paid.");
                fetchBills(currentUser);
            })
            .catch(error => alert("Error paying bill."));
    };

    // --- NEW: DELETE ACCOUNT FUNCTION ---
    const handleDeleteAccount = () => {
        const confirmDelete = window.confirm("Are you sure? This cannot be undone.");
        if (confirmDelete) {
            axios.delete(`http://localhost:8080/api/auth/${currentUser}`)
                .then(() => {
                    alert("Account Deleted.");
                    localStorage.clear(); // Clear session
                    navigate('/'); // Go back to login
                })
                .catch(err => alert("Error deleting account: " + err.message));
        }
    };

    return (
        <div className="dashboard-container">
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                <h2>My Bills Dashboard</h2>
                <span style={{ color: '#555' }}>Logged in as: <strong>{currentUser}</strong></span>
            </div>

            <div style={{ marginBottom: '20px', display: 'flex', gap: '10px' }}>
                <Link to="/notices">
                    <button style={{ padding: '10px 20px', backgroundColor: '#ffc107', border: 'none', borderRadius: '5px', cursor: 'pointer', fontWeight: 'bold', color: '#333' }}>
                        View Service Alerts
                    </button>
                </Link>

                <button
                    onClick={() => { localStorage.removeItem('loggedInUser'); navigate('/'); }}
                    style={{ padding: '10px 20px', backgroundColor: '#007bff', border: 'none', borderRadius: '5px', cursor: 'pointer', fontWeight: 'bold', color: 'white' }}
                >
                    Log Out
                </button>
            </div>

            <table className="bill-table">
                <thead>
                <tr>
                    <th>Provider</th>
                    <th>Amount</th>
                    <th>Due Date</th>
                    <th>Status</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                {bills.length === 0 ? (
                    <tr><td colSpan="5" style={{textAlign:'center', padding:'20px'}}>No bills found for {currentUser}.</td></tr>
                ) : (
                    bills.map(bill => (
                        <tr key={bill.id}>
                            <td>{bill.provider}</td>
                            <td>${bill.amount}</td>
                            <td>{bill.dueDate}</td>
                            <td>
                                {bill.paid ? <span className="status-paid">PAID</span> : <span className="status-unpaid">UNPAID</span>}
                            </td>
                            <td>
                                {!bill.paid && (
                                    <button className="pay-btn" onClick={() => handlePay(bill.id)}>Pay Bill</button>
                                )}
                            </td>
                        </tr>
                    ))
                )}
                </tbody>
            </table>

            <div style={{ marginTop: '50px', borderTop: '1px solid #eee', paddingTop: '20px', textAlign: 'center' }}>
                <button
                    onClick={handleDeleteAccount}
                    style={{ padding: '10px 20px', backgroundColor: '#dc3545', color: 'white', border: 'none', borderRadius: '5px', cursor: 'pointer' }}
                >
                    Delete My Account
                </button>
            </div>
        </div>
    );
}

export default DashboardScene;