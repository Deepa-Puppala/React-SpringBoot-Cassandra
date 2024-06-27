import React from "react";

import "./EditEmployee.scss";
import EmployeeForm from "./EmployeeForm";
import Employee from "./EmployeeList";

interface EditEmployeeProps {
  onClose: () => void;
  employee: Employee | null;
}
const EditEmployee: React.FC<EditEmployeeProps> = ({ onClose, employee }) => {
  return (
    <div className="container">
      <div className="box">
        <EmployeeForm isEdit={true} onEditClose={onClose} employee={employee} />
      </div>
    </div>
  );
};

export default EditEmployee;
