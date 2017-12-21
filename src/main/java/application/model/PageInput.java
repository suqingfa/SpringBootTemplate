package application.model;

import lombok.Data;
import org.springframework.data.domain.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@Validated
public class PageInput
{
    @Min(0)
    private int page = 0;
    @Min(1)
    @Max(20)
    private int size = 10;

    public Pageable getPageableSortByTime()
    {
        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "time");
        return new PageRequest(page, size, new Sort(order));
    }
}
