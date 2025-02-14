package dev.library.backend.dto.mappers;

import dev.library.backend.dto.response.CategoryResponseDto;
import dev.library.backend.models.Category;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-14T16:24:33+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public Category toEntity(CategoryResponseDto categoryResponseDto) {
        if ( categoryResponseDto == null ) {
            return null;
        }

        Category category = new Category();

        category.setId( categoryResponseDto.getId() );
        category.setName( categoryResponseDto.getName() );

        return category;
    }

    @Override
    public CategoryResponseDto toDataTransferObject(Category entity) {
        if ( entity == null ) {
            return null;
        }

        CategoryResponseDto.CategoryResponseDtoBuilder categoryResponseDto = CategoryResponseDto.builder();

        categoryResponseDto.id( entity.getId() );
        categoryResponseDto.name( entity.getName() );

        return categoryResponseDto.build();
    }

    @Override
    public List<CategoryResponseDto> toDataTransferObjects(List<Category> entities) {
        if ( entities == null ) {
            return null;
        }

        List<CategoryResponseDto> list = new ArrayList<CategoryResponseDto>( entities.size() );
        for ( Category category : entities ) {
            list.add( toDataTransferObject( category ) );
        }

        return list;
    }
}
