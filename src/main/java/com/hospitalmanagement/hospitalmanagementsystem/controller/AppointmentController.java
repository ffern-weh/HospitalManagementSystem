package com.hospitalmanagement.hospitalmanagementsystem.controller;

import com.hospitalmanagement.hospitalmanagementsystem.entity.Appointment;
import com.hospitalmanagement.hospitalmanagementsystem.service.AppointmentService;
import com.hospitalmanagement.hospitalmanagementsystem.service.DoctorService;
import com.hospitalmanagement.hospitalmanagementsystem.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @GetMapping("/appointments")
    public String listAppointments(Model model) {
        model.addAttribute("appointments", appointmentService.getAllAppointments());
        return "appointments";
    }

    @GetMapping("/book-appointment")
    public String showBookAppointmentForm(Model model) {
        model.addAttribute("doctors", doctorService.getAllDoctors());
        model.addAttribute("patients", patientService.getAllPatients());
        return "book-appointment";
    }

    @PostMapping("/book-appointment")
    public String bookAppointment(@RequestParam Long patientId, @RequestParam Long doctorId, @RequestParam String appointmentDate) {
        Appointment appointment = new Appointment();
        appointment.setPatient(patientService.findById(patientId));
        appointment.setDoctor(doctorService.findById(doctorId));
        appointment.setAppointmentDate(LocalDate.parse(appointmentDate));
        appointmentService.addAppointment(appointment);
        return "redirect:/appointments";
    }
}
