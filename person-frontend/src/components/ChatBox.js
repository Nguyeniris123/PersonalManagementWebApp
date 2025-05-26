import React, { useEffect, useState, useRef } from "react";
import { db } from "../firebase";
import { collection, addDoc, query, orderBy, onSnapshot, serverTimestamp, } from "firebase/firestore";

const ChatBox = ({ userId, trainerId }) => {
    const [messages, setMessages] = useState([]);
    const [input, setInput] = useState("");
    const messagesEndRef = useRef(null);

    const chatId = userId < trainerId ? `${userId}_${trainerId}` : `${trainerId}_${userId}`;

    useEffect(() => {
        const q = query(
            collection(db, "chats", chatId, "messages"),
            orderBy("timestamp")
        );
        const unsubscribe = onSnapshot(q, (snapshot) => {
            setMessages(snapshot.docs.map(doc => ({ id: doc.id, ...doc.data() })));
        });
        return unsubscribe;
    }, [chatId]);

    useEffect(() => {
        messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
    }, [messages]);

    const sendMessage = async (e) => {
        e.preventDefault();
        if (!input.trim()) return;
        await addDoc(collection(db, "chats", chatId, "messages"), {
            senderId: userId,
            text: input,
            timestamp: serverTimestamp(),
        });
        setInput("");
    };

    return (
        <div style={{ border: "1px solid #ccc", borderRadius: 8, padding: 16, width: 400, margin: "0 auto" }}>
            <div style={{ height: 250, overflowY: "auto", marginBottom: 8 }}>
                {messages.map(msg => (
                    <div key={msg.id} style={{ textAlign: msg.senderId === userId ? "right" : "left", marginBottom: 8 }}>
                        <div>
                            <span style={{ background: "#eee", borderRadius: 4, padding: "6px 12px", margin: 2, display: "inline-block" }}>
                                {msg.text}
                            </span>
                        </div>
                        <div>
                            <span style={{
                                fontSize: 12,
                                color: "#888",
                                margin: msg.senderId === userId ? "0 4px 0 0" : "0 0 0 4px",
                                display: "inline-block"
                            }}>
                                {msg.timestamp ? new Date(msg.timestamp.seconds * 1000).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }) : ""}
                            </span>
                        </div>
                    </div>
                ))}
                <div ref={messagesEndRef} />
            </div>
            <form onSubmit={sendMessage} style={{ display: "flex", gap: 8 }}>
                <input
                    value={input}
                    onChange={e => setInput(e.target.value)}
                    style={{ flex: 1, padding: 8 }}
                    placeholder="Nhập tin nhắn..."
                />
                <button type="submit">Gửi</button>
            </form>
        </div>
    );
};

export default ChatBox;