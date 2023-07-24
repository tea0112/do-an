package com.thai.doan.dto.response;

import com.thai.doan.dao.model.Schedule;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SchedulesResponse {
  private List<Schedule> schedules;
}
