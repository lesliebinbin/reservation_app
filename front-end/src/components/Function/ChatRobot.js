import React from "react";
import { ThemeProvider } from "styled-components";
import ChatBot from "react-simple-chatbot";

const theme = {
  background: "#f5f8fb",
  headerBgColor: "#F0c762",
  headerFontColor: "#fff",
  headerFontSize: "20px",
  botBubbleColor: "#F0c762",
  botFontColor: "#fff",
  userBubbleColor: "#fff",
  userFontColor: "#404156"
};

const steps = [
  {
    id: "1",
    message: "Hi there 👋 I'm Mindbot",
    trigger: "2"
  },
  {
    id: "2",
    options: [
      { value: 1, label: "Hi Mindbot", trigger: "3" },
      { value: 2, label: "Bye Mindbot", trigger: "6" }
    ]
  },
  {
    id: "3",
    message: "What is your name?",
    trigger: "4"
  },
  {
    id: "4",
    user: true,
    trigger: "5"
  },
  {
    id: "5",
    message: "{previousValue}, That's a nice name",
    trigger: "7"
  },
  {
    id: "6",
    message: "Nice to meet you, Bye",
    end: true
  },
  {
    id: "7",
    options: [{ value: 1, label: "Thanks", trigger: "8" }]
  },
  {
    id: "8",
    message: "I'm an emotional assistant",
    trigger: "9"
  },
  {
    id: "9",
    message:
      "I'm like a wise little person you can consult with during difficult times, and not so difficult times",
    trigger: "10"
  },
  {
    id: "10",
    options: [
      { value: 1, label: "That sounds cool", trigger: "11" },
      { value: 2, label: "You're a person?", trigger: "19" }
    ]
  },
  { id: "11", message: "I agree!", trigger: "12" },
  {
    id: "12",
    message:
      "Do you already know about mood tracking and practicing good thinking hygiene?",
    trigger: "13"
  },
  {
    id: "13",
    options: [
      { value: 1, label: "Ya, I sure do", trigger: "14" },
      { value: 2, label: "Can you remind me?", trigger: "21" }
    ]
  },
  {
    id: "14",
    message:
      "Oh good! So here's my challenge: I'll check in with you every day for 2 weeks.",
    trigger: "15"
  },
  {
    id: "15",
    message: "This will be our chance to get to know each other.",
    trigger: "16"
  },
  {
    id: "16",
    message: "Do you accept this challenge?",
    trigger: "17"
  },
  {
    id: "17",
    options: [
      { value: 1, label: "I accept", trigger: "18" },
      { value: 2, label: "I'm not sure", trigger: "25" }
    ]
  },
  {
    id: "18",
    message: "Great! I'm excited to get to know you",
    end: true
  },
  {
    id: "19",
    message: "I'm not human, but in a way, I'm still a person",
    trigger: "20"
  },
  {
    id: "20",
    options: [{ value: 1, label: "Oh", trigger: "12" }]
  },
  {
    id: "21",
    message: "Sure!",
    trigger: "22"
  },
  {
    id: "22",
    message:
      "Mood tracking and thinking hygiene-among other useful concepts are skills you'll learn as you practice Cognitive Behavioral Therapy",
    trigger: "23"
  },
  {
    id: "23",
    message:
      "Skills like these can help you challenge your thoughts so you can make positive changes to your feelings and behavior",
    trigger: "24"
  },
  {
    id: "24",
    options: [{ value: 1, label: "Got it", trigger: "14" }]
  },
  {
    id: "25",
    message: "That's fine, you can consider about it",
    trigger: "6"
  }
];

const ChatRobot = () => {
  return (
    <div className="chat-container">
      <ThemeProvider theme={theme}>
        <ChatBot steps={steps} />
      </ThemeProvider>
    </div>
  );
};

export default ChatRobot;
