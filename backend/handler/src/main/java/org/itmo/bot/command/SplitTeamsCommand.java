package org.itmo.bot.command;

import lombok.RequiredArgsConstructor;
import org.itmo.bot.service.StudentService;
import org.itmo.bot.service.TeamSeparationService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SplitTeamsCommand extends Command{
    private final TeamSeparationService teamSeparationService;
    private final StudentService studentService;

    @Override
    public void execute(long chatId) {
        var chatIds = studentService.getAllPresentChatIds();
        var teams = teamSeparationService.createTeams(chatIds);
        teamSeparationService.sendEmojies(teams);
    }
}
