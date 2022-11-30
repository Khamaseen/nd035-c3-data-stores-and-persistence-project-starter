package com.udacity.jdnd.course3.critter.controllers;

import com.udacity.jdnd.course3.critter.controllers.dtos.ScheduleDTO;
import com.udacity.jdnd.course3.critter.data.Schedule;
import com.udacity.jdnd.course3.critter.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    public static Schedule convertScheduleDTOToSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();

        schedule.setDate(scheduleDTO.getDate());
        schedule.setInvolvedSkills(scheduleDTO.getActivities());

        return schedule;
    }

    public static ScheduleDTO convertScheduleToSchedulDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();

        scheduleDTO.setId(schedule.getId());
        scheduleDTO.setEmployeeIds(schedule.getEmployees().stream()
                .map(employee -> employee.getId())
                .collect(Collectors.toList()));
        scheduleDTO.setPetIds(schedule.getPets().stream()
                .map(pet -> pet.getId())
                .collect(Collectors.toList())
        );
        scheduleDTO.setDate(schedule.getDate());
        scheduleDTO.setActivities(schedule.getInvolvedSkills());

        return scheduleDTO;
    }

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        return convertScheduleToSchedulDTO(this.scheduleService.createSchedule(convertScheduleDTOToSchedule(scheduleDTO), scheduleDTO.getEmployeeIds(), scheduleDTO.getPetIds()));
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        return this.scheduleService.getAllSchedules().stream()
                .map(ScheduleController::convertScheduleToSchedulDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        return this.scheduleService.getAllSchedulesForPet(petId).stream()
                .map(ScheduleController::convertScheduleToSchedulDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        return this.scheduleService.getAllSchedulesForEmployee(employeeId).stream()
                .map(ScheduleController::convertScheduleToSchedulDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        return this.scheduleService.getAllSchedulesForCustomer(customerId).stream()
                .map(ScheduleController::convertScheduleToSchedulDTO)
                .collect(Collectors.toList());
    }
}
