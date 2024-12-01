package hn.unah.proyecto.ModelMapper;

import org.modelmapper.ModelMapper;

public class ModelMapperSingleton {
    public static ModelMapper modelMapper;
    
    private ModelMapperSingleton(){}

    public static ModelMapper getInstancia(){
        if(modelMapper == null){
            modelMapper = new ModelMapper();
        }
        return modelMapper;
    }
}
