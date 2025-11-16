import { React, Routes, Route } from '../src/utils/import.js';

import './App.css';

import { AppContext, AppProvider } from 'src/context/index.js';

import { AnsweringPage, DRLPage, NotFoundPage, HomePage, IntroductionPage, LoginPage, MyUserPage, NotificationPage, RegisterPage } from './pages/index.js'
import { useContext } from 'react';
import AuthCallback from './components/ui/AuthCallback/AuthCallback.js';
import GoogleLogin from './components/ui/LoginWithGoogle/LoginWithGoogle.js';

function App() {
  return (
    <AppProvider>
      <Routes>
        <Route path='/homepage' element={<HomePage />  }/>
        <Route path='/cards/:slug' element={<AnsweringPage/>}/>
        <Route path='/introduction' element={<IntroductionPage />} />
        <Route path='/users/notification' element={<NotificationPage />} />
        <Route path='/behaviour-score' element={<DRLPage/>} />
        {/*<Route path="/*" element={<NotFoundPage />} />*/}
        <Route path='/signin' element={<RegisterPage />} />
        <Route path='/login' element={<LoginPage />} />
        <Route path='/users/:username' element={<MyUserPage />} />
        <Route path='/users/me' element={<MyUserPage />} />
        <Route path="/" element={<GoogleLogin />} />
        <Route path="/auth/callback" element={<AuthCallback />} />
      </Routes>
    </AppProvider>
  );  
}

export default App;
        
