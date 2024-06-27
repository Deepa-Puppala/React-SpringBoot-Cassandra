import "./App.css";
import EmployeeList from "./components/EmployeeList";
import EmployeeForm from "./components/EmployeeForm";
function App() {
  return (
    <>
      <EmployeeForm isEdit={false} />
      <EmployeeList />
    </>
  );
}

export default App;
