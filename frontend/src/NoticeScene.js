import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';

function NoticeScene() {
    const [notices, setNotices] = useState([]);

    useEffect(() => {
        axios.get('http://localhost:8080/api/notices')
            .then(response => setNotices(response.data))
            .catch(error => console.error("Error fetching notices:", error));
    }, []);

    return (
        <div style={{ padding: '20px', maxWidth: '600px', margin: 'auto', textAlign: 'center' }}>
            <h2>Service Notices</h2>

            {notices.length === 0 ? (
                <p>No active alerts.</p>
            ) : (
                <div style={{ textAlign: 'left', backgroundColor: '#fff3cd', padding: '20px', borderRadius: '8px', border: '1px solid #ffeeba' }}>
                    {notices.map((notice, index) => (
                        <div key={index} style={{ marginBottom: '15px', borderBottom: '1px solid #e8e8e8', paddingBottom: '10px' }}>
                            <strong style={{ color: '#856404', fontSize: '1.1em' }}>{notice.provider}:</strong>
                            <p style={{ margin: '5px 0', color: '#555' }}>{notice.message}</p>
                        </div>
                    ))}
                </div>
            )}

            <div style={{ marginTop: '30px' }}>
                <Link to="/dashboard" style={{ textDecoration: 'none', color: 'white', backgroundColor: '#007bff', padding: '10px 20px', borderRadius: '5px' }}>
                    ← Back to Dashboard
                </Link>
            </div>
        </div>
    );
}

export default NoticeScene;