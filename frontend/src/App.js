import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import LoginScene from './LoginScene';
import DashboardScene from './DashboardScene';
import NoticeScene from './NoticeScene';
import ProviderDashboardScene from './ProviderDashboardScene'; // <--- MAKE SURE THIS IS HERE
import AdminDashboardScene from './AdminDashboardScene';
function App() {
    return (
        <Router>
            <div style={{ padding: '20px', fontFamily: 'Arial, sans-serif' }}>
                <nav style={{marginBottom: '20px', borderBottom: '1px solid #ccc', paddingBottom: '10px' }}>
                    <Link to="/" style={{align: 'middle', marginRight: '15px', textDecoration: 'none', color: 'blue' }}>Login</Link>
                </nav>

                <Routes>
                    <Route path="/" element={<LoginScene />} />
                    <Route path="/dashboard" element={<DashboardScene />} />
                    <Route path="/notices" element={<NoticeScene />} />
                    <Route path="/provider" element={<ProviderDashboardScene />} /> {/* <--- AND THIS */}
                    <Route path="/admin" element={<AdminDashboardScene />} />
                </Routes>
            </div>
        </Router>
    );
}

export default App;