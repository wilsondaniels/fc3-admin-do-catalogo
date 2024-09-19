package com.fullcycle.admin.catalogo.application.category.update;


import com.fullcycle.admin.catalogo.application.category.create.CreateCategoryCommand;
import com.fullcycle.admin.catalogo.domain.category.Category;
import com.fullcycle.admin.catalogo.domain.category.CategoryGateway;
import com.fullcycle.admin.catalogo.domain.category.CategoryID;
import com.fullcycle.admin.catalogo.domain.exception.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.text.html.Option;
import java.util.Objects;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class UpdatedCategoryUseCaseTest {

    @InjectMocks
    private DefaultUpdateCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @BeforeEach
    void cleanUp() {
        Mockito.reset(categoryGateway);
    }

    @Test
    public void givenAValidCommand_whenCallsUpdateCategory_shouldReturnCategoryId() {

        final var aCategory = Category.newCategory("Film", null, true);

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var expectedId = aCategory.getId();
        final var aCommand = UpdateCategoryCommand.with(
                                expectedId.getValue(),
                                expectedName,
                                expectedDescription,
                                expectedIsActive);

        Mockito.when(categoryGateway.findById(expectedId))
                .thenReturn(Optional.of(aCategory.clone()));

        Mockito.when(categoryGateway.update(Mockito.any()))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand).get();

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        Mockito.verify(categoryGateway, Mockito.times(1)).findById(expectedId);
        Mockito.verify(categoryGateway, Mockito.times(1)).update(argThat(
                aUpdatedCategory ->
                        Objects.equals(expectedName, aUpdatedCategory.getName())
                                && Objects.equals(expectedDescription, aUpdatedCategory.getDescription())
                                && Objects.equals(expectedIsActive, aUpdatedCategory.isActive())
                                && Objects.equals(expectedId, aUpdatedCategory.getId())
                                && Objects.equals(aCategory.getCreatedAt(), aUpdatedCategory.getCreatedAt())
                                && aCategory.getUpdatedAt().isBefore(aUpdatedCategory.getUpdatedAt())
                                && Objects.equals(aCategory.getDeletedAt(), aUpdatedCategory.getDeletedAt())
        ));
    }

    @Test
    public void givenAInvalidName_whenCallsUpdateCategory_thenShouldReturnDomainException() {
        final var aCategory = Category.newCategory("Film", null, true);

        final String expectedName = null;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedId = aCategory.getId();

        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;

        final var aCommand = UpdateCategoryCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedDescription,
                expectedIsActive);

        Mockito.when(categoryGateway.findById(expectedId))
                .thenReturn(Optional.of(aCategory.clone()));

        final var notification = useCase.execute(aCommand).getLeft();

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());

        Mockito.verify(categoryGateway, times(0)).update(any());
    }

    @Test
    public void givenAValidInactivateCommand_whenCallsUpdateCategory_shouldReturnInactiveCategoryId() {

        final var aCategory = Category.newCategory("Film", null, true);

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = false;

        final var expectedId = aCategory.getId();
        final var aCommand = UpdateCategoryCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedDescription,
                expectedIsActive);

        Mockito.when(categoryGateway.findById(expectedId))
                .thenReturn(Optional.of(aCategory.clone()));

        Mockito.when(categoryGateway.update(Mockito.any()))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());

        Assertions.assertTrue(aCategory.isActive());
        Assertions.assertNull(aCategory.getDeletedAt());

        final var actualOutput = useCase.execute(aCommand).get();

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        Mockito.verify(categoryGateway, Mockito.times(1)).findById(expectedId);
        Mockito.verify(categoryGateway, Mockito.times(1)).update(argThat(
                aUpdatedCategory ->
                        Objects.equals(expectedName, aUpdatedCategory.getName())
                                && Objects.equals(expectedDescription, aUpdatedCategory.getDescription())
                                && Objects.equals(expectedIsActive, aUpdatedCategory.isActive())
                                && Objects.equals(expectedId, aUpdatedCategory.getId())
                                && Objects.equals(aCategory.getCreatedAt(), aUpdatedCategory.getCreatedAt())
                                && aCategory.getUpdatedAt().isBefore(aUpdatedCategory.getUpdatedAt())
                                && Objects.nonNull(aUpdatedCategory.getDeletedAt())
        ));
    }

    @Test
    public void givenAValidCommand_whenGatewayThrowsRandomException_shouldReturnAException() {

        final var aCategory = Category.newCategory("Film", null, true);

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedId = aCategory.getId();
        final var expectedErrorMessage = "Gateway exception";

        final var aCommand = UpdateCategoryCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedDescription,
                expectedIsActive);

        Mockito.when(categoryGateway.findById(expectedId))
                .thenReturn(Optional.of(aCategory.clone()));

        Mockito.when(categoryGateway.update(Mockito.any()))
                .thenThrow(new IllegalStateException(expectedErrorMessage));

        final var notification = useCase.execute(aCommand).getLeft();

        Assertions.assertTrue(aCategory.isActive());
        Assertions.assertNull(aCategory.getDeletedAt());

        Mockito.verify(categoryGateway, Mockito.times(1)).update(argThat(
                aUpdatedCategory ->
                        Objects.equals(expectedName, aUpdatedCategory.getName())
                                && Objects.equals(expectedDescription, aUpdatedCategory.getDescription())
                                && Objects.equals(expectedIsActive, aUpdatedCategory.isActive())
                                && Objects.equals(expectedId, aUpdatedCategory.getId())
                                && Objects.equals(aCategory.getCreatedAt(), aUpdatedCategory.getCreatedAt())
                                && aCategory.getUpdatedAt().isBefore(aUpdatedCategory.getUpdatedAt())
                                && Objects.isNull(aUpdatedCategory.getDeletedAt())
        ));
    }

    @Test
    public void givenACommandWithInvalidId_whenCallsUpdateCategory_thenShouldReturnNotFoundException() {

        final String expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedId = "123";
        final var expectedErrorMessage = "Category with ID 123 was not found";
        final var expectedErrorCount = 1;

        final var aCommand = UpdateCategoryCommand.with(
                expectedId,
                expectedName,
                expectedDescription,
                expectedIsActive);

        Mockito.when(categoryGateway.findById(CategoryID.from(expectedId)))
                .thenReturn(Optional.empty());

        final var actualException =
                Assertions.assertThrows(DomainException.class, () -> useCase.execute(aCommand));

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

        Mockito.verify(categoryGateway, Mockito.times(1)).findById(CategoryID.from(expectedId));
        Mockito.verify(categoryGateway, Mockito.times(0)).update(any());
    }
}
