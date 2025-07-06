package by.kolp.api.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectConfigFile {

    private final ModelMapper modelMapper;

    @Autowired
    public ProjectConfigFile(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

}
