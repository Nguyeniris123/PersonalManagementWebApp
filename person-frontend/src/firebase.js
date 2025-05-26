// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
import { getAnalytics } from "firebase/analytics";
import { getFirestore } from "firebase/firestore";
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
// For Firebase JS SDK v7.20.0 and later, measurementId is optional
const firebaseConfig = {
  apiKey: "AIzaSyCnIewV5EiijuaaAlQj1K5nMLlyWLCJGus",
  authDomain: "person-bc573.firebaseapp.com",
  projectId: "person-bc573",
  storageBucket: "person-bc573.firebasestorage.app",
  messagingSenderId: "603605689755",
  appId: "1:603605689755:web:c40ac25da341713998ad3e",
  measurementId: "G-D63BS6LM0B"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
const analytics = getAnalytics(app);
export const db = getFirestore(app);