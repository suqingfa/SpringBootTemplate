package application.util;

import org.springframework.data.domain.*;

public class Page
{
    public static Pageable getPage(int page)
    {
        return getPage(page, 10);
    }

    public static Pageable getPage(int page, int size)
    {
        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "time");
        PageRequest pageRequest = new PageRequest(page, size, new Sort(order));
        return pageRequest;
    }
}
