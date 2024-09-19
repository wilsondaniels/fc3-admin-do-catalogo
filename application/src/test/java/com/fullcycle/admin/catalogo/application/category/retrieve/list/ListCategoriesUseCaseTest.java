package com.fullcycle.admin.catalogo.application.category.retrieve.list;

import com.fullcycle.admin.catalogo.domain.category.Category;
import com.fullcycle.admin.catalogo.domain.category.CategoryGateway;
import com.fullcycle.admin.catalogo.domain.category.CategorySearchQuery;
import com.fullcycle.admin.catalogo.domain.pagination.Pagination;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ListCategoriesUseCaseTest {

    @InjectMocks
    private DefaultListCategoriesUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @BeforeEach
    void cleanUp() {
        Mockito.reset(categoryGateway);
    }

    @Test
    public void givenAValidQuery_whenCallsListCategories_shouldReturnCategories() {

        final var categories = List.of(
                Category.newCategory("Filmes", null, true),
                Category.newCategory("Series", null, true)
        );

        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "";
        final var expectedSort = "createdAt";
        final var expectedDirection = "asc";
        final var aQuery = new CategorySearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirection);

        final var expectedPagination = new Pagination<>(expectedPage, expectedPerPage, categories.size(), categories);

        final var expectedItensCount = 2;
        final var expectedResults = expectedPagination.map(CategoryListOutput::from);

        Mockito.when(categoryGateway.findAll(Mockito.eq(aQuery))).thenReturn(expectedPagination);

        final var actualResult = useCase.execute(aQuery);

        Assertions.assertEquals(expectedItensCount, actualResult.items().size());
        Assertions.assertEquals(expectedResults, actualResult);
        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(categories.size(), actualResult.total());
    }

    @Test
    public void givenAValidQuery_whenHasNoResults_thanShouldReturnEmptyCategories() {

        final var categories = List.<Category>of();

        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "";
        final var expectedSort = "createdAt";
        final var expectedDirection = "asc";
        final var aQuery = new CategorySearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirection);

        final var expectedPagination = new Pagination<>(expectedPage, expectedPerPage, categories.size(), categories);

        final var expectedItensCount = 0;
        final var expectedResults = expectedPagination.map(CategoryListOutput::from);

        Mockito.when(categoryGateway.findAll(Mockito.eq(aQuery))).thenReturn(expectedPagination);

        final var actualResult = useCase.execute(aQuery);

        Assertions.assertEquals(expectedItensCount, actualResult.items().size());
        Assertions.assertEquals(expectedResults, actualResult);
        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(categories.size(), actualResult.total());
    }

    @Test
    public void givenAValidQuery_whenGatewayThrosException_shouldReturnException() {

        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "";
        final var expectedSort = "createdAt";
        final var expectedDirection = "asc";
        final var expectedErrorMessage = "Gateway error";
        final var aQuery = new CategorySearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirection);

        Mockito.when(categoryGateway.findAll(Mockito.eq(aQuery)))
                .thenThrow(new IllegalStateException(expectedErrorMessage));

        final var actualException = Assertions.assertThrows(
                IllegalStateException.class, () -> useCase.execute(aQuery));

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
    }
}
