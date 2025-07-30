package com.beared.queueservice.service;

import com.beared.queueservice.exception.ResourceNotFoundException;
import com.beared.queueservice.model.Queue;
import com.beared.queueservice.repository.QueueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QueueService {

    @Autowired
    private QueueRepository queueRepository;


    public Queue createQueue(Queue queue) {
        if (queueRepository.existsByQueueName(queue.getQueueName())) {
            throw new IllegalArgumentException("Queue with name '" + queue.getQueueName() + "' already exists.");
        }
        return queueRepository.save(queue);
    }


    public Queue getQueueById(Long id) {
        return queueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Queue not found with id: " + id));
    }


    public List<Queue> getAllQueues() {
        return queueRepository.findAll();
    }


    public void deleteQueue(Long id) {
        Queue queue = getQueueById(id);

    }

}
