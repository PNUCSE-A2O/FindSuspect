package org.example.service;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.example.dto.HistoryDTO;
import org.example.dto.PageHistory;
import org.example.dto.ResultDTO;
import org.example.entity.History;
import org.example.exception.BadRequestException;
import org.example.repository.HistoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HistoryService {
    private final HistoryRepository historyRepository;

    public PageHistory getHistory(Pageable pageable) {
        Page<History> histories = historyRepository.findAll(pageable);
        List<HistoryDTO> his = histories.getContent().stream().map(History::toDTO).toList();
        return new PageHistory(his,histories.getTotalElements(), histories.getTotalPages());
    }

    public void deleteHistory(int historyId) {
        historyRepository.findById(historyId).ifPresent(historyRepository::delete);
    }
}
