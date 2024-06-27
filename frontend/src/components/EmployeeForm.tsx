import React, { useState, useEffect } from "react";

import "./EmployeeList.scss";
import apiClient from "../apiClient";
import { Employee } from "./EmployeeList";

interface Props {
  onEditClose?: () => void;
  isEdit: boolean;
  employee?: Employee | null;
}
const EmployeeForm: React.FC<Props> = ({ isEdit, onEditClose, employee }) => {
  const [name, setName] = useState("");
  const [employeetype, setemployeetype] = useState("");
  const [industry, setIndustry] = useState("");

  useEffect(() => {
    if (isEdit && employee) {
      // Populate form fields with employee data when in edit mode
      setName(employee.name);
      setemployeetype(employee.employeetype);
      setIndustry(employee.industry);
    }
  }, [isEdit, employee]);

  const handleSubmit = (event: React.FormEvent) => {
    event.preventDefault();

    // Prepare data to send
    const formData = {
      name: name,
      employeetype: employeetype,
      industry: industry,
    };

    if (!isEdit) {
      // POST request using Axios
      apiClient
        .post("/api/employees", formData)
        .then((response) => {
          console.log(response);
          alert("Data sent successfully");
          setName("");
          setemployeetype("");
          setIndustry("");
        })
        .catch((error) => {
          console.error("Error sending data:", error);
        });
    } else {
      apiClient
        .put(`/api/employees/${employee?.id}`, formData)
        .then((response) => {
          console.log(response);
          alert("Data updated successfully");
          setName("");
          setemployeetype("");
          setIndustry("");
          onEditClose && onEditClose();
        })
        .catch((error) => {
          console.error("Error sending data:", error);
        });
    }
  };
  return (
    <div>
      {isEdit ? (
        <h1>Edit Employee details</h1>
      ) : (
        <h1>Enter Employee details</h1>
      )}
      <form className="form" onSubmit={handleSubmit}>
        <div className="form-control">
          <label htmlFor="name">Name:</label>
          <input
            type="text"
            id="name"
            value={name}
            onChange={(e) => setName(e.target.value)}
          />
        </div>
        <div className="form-control">
          <label htmlFor="employeetype">employeetype:</label>
          <input
            type="employeetype"
            id="employeetype"
            value={employeetype}
            onChange={(e) => setemployeetype(e.target.value)}
          />
        </div>
        <div className="form-control">
          <label htmlFor="industry">Industry:</label>
          <input
            type="text"
            id="industry"
            value={industry}
            onChange={(e) => setIndustry(e.target.value)}
          />
        </div>
        <div className="btns">
          <button className="btn" type="submit">
            Submit
          </button>

          {isEdit && (
            <button className="btn" onClick={onEditClose}>
              Close
            </button>
          )}
        </div>
      </form>
    </div>
  );
};

export default EmployeeForm;
