package org.itmo.bot;

import org.itmo.bot.service.TeamSeparationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class BotApplicationTests {

	@Autowired
	private TeamSeparationService teamSeparationService;

	@Test
	public void positiveTeamSeparationTest() {
		final int TEAM_AMOUNT = 26;
		final int STUDENT_AMOUNT = 5;

		List<Long> students = new ArrayList<>(TEAM_AMOUNT * STUDENT_AMOUNT);

		for (long i = 0; i < TEAM_AMOUNT * STUDENT_AMOUNT; ++i) {
			students.add(i);
		}

		var teams = teamSeparationService.createTeams(students);

		Assertions.assertEquals(TEAM_AMOUNT, teams.size());
		for (var team: teams) {
			Assertions.assertEquals(STUDENT_AMOUNT, team.size());
		}

	}
}
