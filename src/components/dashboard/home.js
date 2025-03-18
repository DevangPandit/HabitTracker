import React, { useState, useEffect } from 'react';
import styled from 'styled-components';
import image from '../photos/nothing.png';

const Container = styled.div`
  background-color: #efeae4;
  min-height: 100vh;
  font-family: "WF Visual Sans", Arial, sans-serif;
`;

const NavigationBar = styled.nav`
  background-color: #1e1e1e; 
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
  height: 100px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.3);
`;

const Title = styled.h1`
  color: #efeae4;  
  font-size: 2rem;
  font-weight: 900;
  margin-left: 100px;
`;

const AddButton = styled.button`
  height: 48px;
  width: 200px;
  padding: 15px 22px;
  margin-right: 60px;
  border-radius: 5px;
  border: none;
  background-color: #ff6b6b;
  color: white; 
  font-size: 1.1rem;
  font-weight: bold;
  cursor: pointer;
  transition: background-color 0.3s ease, transform 0.2s;

  &:hover {
    background-color: #ffb86c; 
    transform: scale(1.05);
  }
`;


const ModalContent = styled.div`
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  z-index: 1000;
  width: 400px;
  max-width: 90%;
`;

const CloseButton = styled.button`
  background-color: transparent;
  border: none;
  font-size: 1.5rem;
  cursor: pointer;
  position: absolute;
  top: 10px;
  right: 10px;
  color: #aaa;

  &:hover {
    color: #000;
  }
`;

const InputContainer = styled.div`
  margin-bottom: 16px;
  margin-top: 25px;
`;

const Label = styled.label`
  display: block;
  margin-bottom: 8px;
  font-weight: bold;
  color: #333;
`;

const Input = styled.input`
  width: 380px;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 1rem;
  transition: border-color 0.3s ease;

  &:focus {
    border-color: #00A6A6;
    outline: none;
  }
`;

const DropDown = styled.select`
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 1rem;

  &:focus {
    border-color: #00A6A6;
    outline: none;
  }
`;

const SubmitButton = styled.button`
  width: 100%;
  padding: 10px;
  background-color: #1e1e1e;
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 1.2rem;
  cursor: pointer;
  transition: background-color 0.3s ease;

  &:hover {
    background-color: #1e1e1e;
  }
`;

const HabitList = styled.ul`
  list-style-type: none;
  padding: 10px;
  margin: 20px;
  margin-bottom: 0;
`;

const HabitItem = styled.li`
  background-color: white;
  padding: 15px;
  margin-bottom: 10px;
  width: 50%;
  height: 160px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
`;

const HabitDetails = styled.div`
  display: flex;
  padding: 10px;
  flex-direction: column;
`;

const ButtonContainer = styled.div`
  display: flex;
  justify-content: space-between;
`;

const HabitName = styled.p`
  margin-top: 0;
  margin-bottom: 5px;
  font-size: 1.3rem;
  font-weight: bold;
  color: black;
`;

const HabitDescription = styled.p`
  font-size: 1.2rem;
  color: #666;
  margin-top: 0;
  margin-bottom: 5px;
`;

const HabitFrequency = styled.span`
  background-color: #007bff;
  color: white;
  margin-top: 5px;
  padding: 5px 10px;
  border-radius: 10px;
  width: 50px;
  text-align: center;
  font-size: 1rem;
`;

const ImageContainer = styled.img`
  width: 500px;
  height: 500px;
  display: block;
  margin: auto;
`;

const DeleteButton = styled.button`
  width: 100%;
  height: 40px;
  font-size: 1rem;
  color: white;
  margin: 10px;
  background-color: red;
  border-radius: 5px;
  border: none;
  padding: 10px;
`;

const DoneButton = styled.button`
  width: 100%;
  height: 40px;
  font-size: 1rem;
  color: white;
  margin: 10px;
  background-color: red;
  border-radius: 5px;
  border: none;
  padding: 10px;
`;

const StreakCounter = styled.button`
  width: 100%;
  height: 40px;
  font-size: 1rem;
  color: white;
  margin: 10px;
  background-color: lightblue;
  border-radius: 5px;
  border: none;
  padding: 10px;
`

function Home() {
    const [modalLayout, setModalLayout] = useState(false);
    const [habit, setHabit] = useState('');
    const [habitdescription, setHabitDescription] = useState('');
    const [frequency, setFrequency] = useState('');
    const [habitList, setHabitList] = useState([]);
    const [error, setError] = useState('');

    useEffect(() => {
      const fetchHabits = async () => {
          const username = localStorage.getItem("username");
          if (!username) {
              console.log("User not logged in");
              return;
          }
          
          try {
              const response = await fetch(`http://localhost:8080/api/${username}/habit`);
              if (!response.ok) {
                  throw new Error("Error fetching habits!");
              }
              const data = await response.json();
  
              if (Array.isArray(data)) {
                  setHabitList(data.map(habit =>({
                    ...habit,
                    lastdone : habit.lastdone || null,
                    streak : habit.streak || 0,
                  }))); 
              } else {
                  setHabitList([]); 
              }
          } catch (err) {
              setError(err.message || "Error occurred!");
          }
      };
  
      fetchHabits();
  }, []);

    const handleHabitForm = () => {
        setModalLayout(true);
    };

    const closeModal = () => {
        setModalLayout(false);
    };

    const handleSubmit = async (e) => {
      e.preventDefault();
      if (!habit.trim() || !habitdescription.trim() || !frequency.trim()) {
          alert("Fill the required fields");
          return;
      }
  
      try {
          const username = localStorage.getItem("username");
          if (!username) {
              alert("User not logged in");
              return;
          }
  
          const response = await fetch(`http://localhost:8080/api/${username}/habit`, {
              method: "POST",
              headers: { "Content-Type": "application/json" },
              body: JSON.stringify({
                  username,
                  name: habit,
                  description: habitdescription,
                  frequency
              }),
          });
  
          if (!response.ok) {
              const errorMessage = await response.text();
              throw new Error(errorMessage || "Error occurred!");
          }
  
          const newHabit = await response.json();
          setHabitList(prevhabitList => [...prevhabitList, newHabit]);
  
          alert("Habit added successfully!");
          setModalLayout(false);
          setHabit("");
          setHabitDescription("");
          setFrequency("");
  
      } catch (err) {
          setError(err.message || "Error occurred!");
      }
  };  

  const handleDoneButton = async (habitId) => {
    try{
      const username = localStorage.getItem("username");
      if(!username){
        alert("User not logged in");
        return;
      }

      const userId = localStorage.getItem("userId");
      if(!userId){
        alert("User noty logged in");
        return;
      }

      const response = await fetch(`http://localhost:8080/api/${userId}/${habitId}/complete`, {
        method: "POST",
        headers: { "Content-Type" : "application/json" },
      })

      if(!response.ok){
        const errorMessage = await response.text();
        throw new Error(errorMessage || "Error while completing the habit");
      }

      const streakResponse = await fetch(`http://localhost:8080/api/${userId}/${habitId}/streak`);

      if(!streakResponse){
        const errorMessage = await response.text();
        throw new Error( errorMessage || "Error Occured during streak!");
      }
      const streakData = streakResponse.json();
      setHabitList((prevhabitList) => prevhabitList.map((habit) => 
        habit.id !== habitId
           ? { ...habit, strak: streakData.streak }
           : habit
    ))

    alert("Habit marked Done Successfullly");

    }catch(err){
      setError(err.message || "Error Occured ");

    }
  }

  const handleDeleteButton = async (habitId) => {
    try{
      const username = localStorage.getItem("username");
      if(!username){
        alert("User not logged in");
        return;
      }

      const response = await fetch(`http://localhost:8080/api/habit/${habitId}`, {
        method: "DELETE"
      });

      if(!response.ok){
        const errorMessage = await response.text();
        throw new Error(errorMessage || "Error Occured");
      }

      setHabitList((prevhabitList) => prevhabitList.filter((habit) => habit.id !== habitId))

    }catch(err){
      setError(err.message || "Error Occured while Deleting Habit")
    }
  };

    return (
        <Container>
            <NavigationBar>
                {error && <p className="error-message">{error}</p>}
                <Title>HabitTracker</Title>
                <AddButton onClick={handleHabitForm}>Add Habit</AddButton>
            </NavigationBar>

            {modalLayout && (
                <ModalContent>
                    <CloseButton onClick={closeModal}>×</CloseButton>
                    <form onSubmit={handleSubmit}>
                        <InputContainer>
                            <Label>Habit</Label>
                            <Input type="text" value={habit} onChange={(e) => setHabit(e.target.value)} required />
                        </InputContainer>
                        <InputContainer>
                            <Label>Description</Label>
                            <Input type="text" value={habitdescription} onChange={(e) => setHabitDescription(e.target.value)} required />
                        </InputContainer>
                        <InputContainer>
                            <Label>Frequency</Label>
                            <DropDown value={frequency} onChange={(e) => setFrequency(e.target.value)} required>
                                <option value="">Choose Frequency</option>
                                <option value="Daily">Daily</option>
                                <option value="Weekly">Weekly</option>
                                <option value="Monthly">Monthly</option>
                            </DropDown>
                        </InputContainer>
                        <SubmitButton type="submit">Add Habit</SubmitButton>
                    </form>
                </ModalContent>
            )}

            <HabitList>
                {habitList.length > 0 ? habitList.map((habitItem, index) => (
                    <HabitItem key={index}>
                        <HabitDetails>
                            <HabitName>{habitItem.name}</HabitName>
                            <HabitDescription>{habitItem.description}</HabitDescription>
                            <HabitFrequency>{habitItem.frequency}</HabitFrequency>
                        </HabitDetails>
                        <ButtonContainer>
                            <DeleteButton onClick={() => handleDeleteButton(habitItem.id)}>Delete</DeleteButton>
                            <DoneButton onClick={()=> handleDoneButton(habitItem.id)}>Done</DoneButton>
                            <StreakCounter>{habitItem.streak || 0}</StreakCounter>
                        </ButtonContainer>
                    </HabitItem>
                )) : (
                    <ImageContainer src={image} alt="No Habits Found" />
                )}
            </HabitList>
        </Container>
    );
}

export default Home;


