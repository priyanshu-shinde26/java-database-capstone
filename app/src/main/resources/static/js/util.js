function selectRole(role) {
  localStorage.setItem('userRole', role);
  alert('Selected role: ' + role); // Replace with modal logic or dashboard redirect
}
