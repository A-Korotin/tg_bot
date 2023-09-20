package org.itmo.bot.service;

import lombok.RequiredArgsConstructor;
import org.itmo.bot.common.dto.TextResponseDTO;
import org.itmo.bot.common.topics.Topic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamSeparationService {


    private final KafkaTemplate<String, TextResponseDTO> template;

    private final String[] EMOJIES = new String[]{"\uD83E\uDD70", "\uD83D\uDC4C", "\uD83D\uDE2D", "\uD83D\uDC8B", "\uD83D\uDE48", "\uD83D\uDE31", "\uD83D\uDE36\u200D\uD83C\uDF2B\uFE0F", "\uD83E\uDD2F", "\uD83D\uDC38", "\uD83D\uDC23", "\uD83C\uDF4C", "\uD83C\uDF46", "\uD83C\uDF54", "❌", "\uD83D\uDEBE", "\uD83C\uDDF7\uD83C\uDDFA", "\uD83D\uDCCE", "\uD83D\uDCBB", "\uD83D\uDEA8", "\uD83C\uDFAF", "\uD83D\uDE0E", "\uD83C\uDF40", "\uD83D\uDC8E", "\uD83C\uDF69", "\uD83C\uDF6C", "\uD83E\uDD75"};

    public List<List<Long>> createTeams(List<Long> studentChatIds) {
        List<List<Long>> teams = new ArrayList<>();

        Collections.shuffle(studentChatIds);

        int teamCap = studentChatIds.size() / 26;
        int dif = studentChatIds.size() % 26;

        for (int i = 0; i < 26; i++) {
            List<Long> team = new ArrayList<>();
            for (int el = 0; el < teamCap; el++) {
                team.add(studentChatIds.get(i * teamCap + el));
            }
            teams.add(team);
        }

        for (int i = 0; i < dif; i++) {
            teams.get(i).add(studentChatIds.get(studentChatIds.size() - studentChatIds.size() % 26 - 1 + dif));
        }

        return teams;
    }

    public void sendEmojies(List<List<Long>> teams) {
        for (int i = 0; i < teams.size(); i++) {
            for (Long id : teams.get(i)) {
                TextResponseDTO textResponseDTO = TextResponseDTO.builder()
                        .chatId(id)
                        .message(EMOJIES[i])
                        .build();

                template.send(Topic.TEXT_RESPONSE_TOPIC, textResponseDTO);
            }
        }
    }

}
