import React, { useState } from "react";

const friendsData = [
  { id: 1, name: "Alice", lastMessage: "Hi there!", online: true },
  { id: 2, name: "Bob", lastMessage: "How are you?", online: false },
  { id: 3, name: "Charlie", lastMessage: "See you soon.", online: true },
];

const messagesData = {
  1: [
    { fromMe: false, text: "Hi Alice!" },
    { fromMe: true, text: "Hello! How are you?" },
  ],
  2: [
    { fromMe: false, text: "Hey Bob, what's up?" },
    { fromMe: true, text: "All good here, thanks!" },
  ],
  3: [
    { fromMe: true, text: "Are we meeting tomorrow?" },
    { fromMe: false, text: "Yes, at 3 PM." },
  ],
};

export default function Messenger() {
  const [selectedFriendId, setSelectedFriendId] = useState(friendsData[0].id);
  const [messages, setMessages] = useState(messagesData);
  const [inputText, setInputText] = useState("");

  const selectedMessages = messages[selectedFriendId] || [];

  const sendMessage = () => {
    if (!inputText.trim()) return;
    const newMsg = { fromMe: true, text: inputText.trim() };
    setMessages({
      ...messages,
      [selectedFriendId]: [...selectedMessages, newMsg],
    });
    setInputText("");
  };

  return (
    <>
      <style>{`
        * {
          box-sizing: border-box;
        }
        body,html,#root {
          margin: 0; padding: 0; height: 100%;
          font-family: Arial, sans-serif;
          background: #f0f2f5;
        }
        .messenger-container {
          max-width: 900px;
          height: 90vh;
          margin: 20px auto;
          display: flex;
          background: #fff;
          border-radius: 10px;
          box-shadow: 0 0 10px rgba(0,0,0,0.1);
          overflow: hidden;
        }
        .friends-list {
          width: 280px;
          border-right: 1px solid #ddd;
          overflow-y: auto;
        }
        .friend-item {
          padding: 15px 20px;
          cursor: pointer;
          border-bottom: 1px solid #eee;
          display: flex;
          justify-content: space-between;
          align-items: center;
          transition: background-color 0.2s;
        }
        .friend-item.selected, .friend-item:hover {
          background: #e6f7ff;
          font-weight: bold;
        }
        .friend-name {
          font-size: 16px;
        }
        .friend-last-message {
          font-size: 12px;
          color: #666;
          margin-top: 3px;
        }
        .online-dot {
          width: 10px;
          height: 10px;
          border-radius: 50%;
          background: #4caf50;
          border: 1.5px solid white;
          box-shadow: 0 0 3px #4caf50;
        }
        .chat-area {
          flex: 1;
          display: flex;
          flex-direction: column;
        }
        .chat-header {
          padding: 15px 20px;
          border-bottom: 1px solid #ddd;
          font-weight: bold;
          font-size: 18px;
          background: #fafafa;
        }
        .chat-messages {
          flex: 1;
          padding: 20px;
          overflow-y: auto;
          background: #f9f9f9;
          display: flex;
          flex-direction: column;
          gap: 12px;
        }
        .message {
          max-width: 70%;
          padding: 10px 15px;
          border-radius: 20px;
          font-size: 14px;
          line-height: 1.3;
          word-wrap: break-word;
        }
        .message.from-me {
          background: #dcf8c6;
          align-self: flex-end;
          border-bottom-right-radius: 0;
        }
        .message.from-other {
          background: #fff;
          border: 1px solid #ddd;
          align-self: flex-start;
          border-bottom-left-radius: 0;
        }
        .chat-input-area {
          padding: 10px 20px;
          border-top: 1px solid #ddd;
          display: flex;
          gap: 10px;
          background: #fafafa;
        }
        .chat-input-area input {
          flex: 1;
          border-radius: 20px;
          border: 1px solid #ccc;
          padding: 8px 15px;
          font-size: 14px;
          outline: none;
        }
        .chat-input-area button {
          background: #1890ff;
          border: none;
          border-radius: 20px;
          color: white;
          font-weight: bold;
          padding: 8px 20px;
          cursor: pointer;
          transition: background-color 0.3s;
        }
        .chat-input-area button:hover {
          background: #0f7ae5;
        }

        /* Responsive */
        @media (max-width: 768px) {
          .messenger-container {
            flex-direction: column;
            height: 100vh;
          }
          .friends-list {
            width: 100%;
            height: 120px;
            display: flex;
            overflow-x: auto;
            border-right: none;
            border-bottom: 1px solid #ddd;
          }
          .friend-item {
            flex: 0 0 auto;
            border-bottom: none;
            border-right: 1px solid #eee;
            min-width: 120px;
            padding: 10px;
            flex-direction: column;
            justify-content: center;
            align-items: center;
          }
          .chat-area {
            flex: 1;
            height: calc(100vh - 120px);
          }
          .chat-messages {
            padding: 10px;
          }
        }
      `}</style>

      <div className="messenger-container">
        <div className="friends-list">
          {friendsData.map(friend => (
            <div
              key={friend.id}
              className={`friend-item ${friend.id === selectedFriendId ? "selected" : ""}`}
              onClick={() => setSelectedFriendId(friend.id)}
            >
              <div>
                <div className="friend-name">{friend.name}</div>
                <div className="friend-last-message">{friend.lastMessage}</div>
              </div>
              {friend.online && <div className="online-dot" />}
            </div>
          ))}
        </div>

        <div className="chat-area">
          <div className="chat-header">
            {friendsData.find(f => f.id === selectedFriendId)?.name || "Chọn người chat"}
          </div>
          <div className="chat-messages">
            {selectedMessages.map((msg, idx) => (
              <div
                key={idx}
                className={`message ${msg.fromMe ? "from-me" : "from-other"}`}
              >
                {msg.text}
              </div>
            ))}
          </div>
          <div className="chat-input-area">
            <input
              type="text"
              placeholder="Nhập tin nhắn..."
              value={inputText}
              onChange={e => setInputText(e.target.value)}
              onKeyDown={e => {
                if(e.key === "Enter") {
                  e.preventDefault();
                  sendMessage();
                }
              }}
            />
            <button onClick={sendMessage}>Gửi</button>
          </div>
        </div>
      </div>
    </>
  );
}
