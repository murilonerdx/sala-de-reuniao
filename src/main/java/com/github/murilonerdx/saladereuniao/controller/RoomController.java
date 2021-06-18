package com.github.murilonerdx.saladereuniao.controller;

import com.github.murilonerdx.saladereuniao.exception.ResourceNotFoundException;
import com.github.murilonerdx.saladereuniao.model.Room;
import com.github.murilonerdx.saladereuniao.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/api/v1/")
public class RoomController {

    @Autowired
    private RoomRepository repository;

    @GetMapping("/rooms")
    public List<Room> getAllRooms(){
        return repository.findAll();
    }

    @GetMapping("/rooms/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable(value="id") Long roomId) throws ResourceNotFoundException {
        Room room = repository.findById(roomId).orElseThrow(()-> new ResourceNotFoundException("Room id not found:: " + roomId));
        return ResponseEntity.ok().body(room);
    }

    @PostMapping("/rooms")
    public Room createRoom(@RequestBody Room room){
        return repository.save(room);
    }

    @PutMapping("/room/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable(value="id") Long roomId, @RequestBody Room roomPut) throws ResourceNotFoundException{
        Room room = repository.findById(roomId).orElseThrow(()-> new ResourceNotFoundException("Room id not found:: " + roomId));
        room.setName(roomPut.getName());
        room.setEndHour(roomPut.getEndHour());
        room.setStartHour(roomPut.getStartHour());
        room.setId(roomId);
        final Room updateRoom = repository.save(room);
        return ResponseEntity.ok(updateRoom);
    }

    @DeleteMapping("/room/{id}")
    public ResponseEntity<?> deleteRoom(@PathVariable(value="id") Long idRoom) throws ResourceNotFoundException {
        repository.findById(idRoom).orElseThrow(()-> new ResourceNotFoundException("Room id not found:: " + idRoom));
        repository.deleteById(idRoom);
        return ResponseEntity.noContent().build();
    }
}
