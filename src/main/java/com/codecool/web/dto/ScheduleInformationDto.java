package com.codecool.web.dto;

import com.codecool.web.model.Day;
import com.codecool.web.model.Schedule;
import java.util.List;

public class ScheduleInformationDto {

    private Schedule schedule;
    private List<Day> days;

    public ScheduleInformationDto(Schedule schedule, List<Day> days) {
        this.schedule = schedule;
        this.days = days;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public List<Day> getDays() {
        return days;
    }

}
