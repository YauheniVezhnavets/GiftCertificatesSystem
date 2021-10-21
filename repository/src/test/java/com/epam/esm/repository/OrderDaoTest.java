package com.epam.esm.repository;


//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotEquals;
//
//
//@ExtendWith(SpringExtension.class)
//@DirtiesContext
//@ContextConfiguration(classes = { OrderDao.class, Paginator.class, EntityManagerFactoryConfig.class},
//        loader = AnnotationConfigContextLoader.class)
//@SpringBootTest
//@Transactional
//@ActiveProfiles("dev")
public class OrderDaoTest {


//    private List<Order> EXPECTED_ORDER = List.of(
//            new Order(1L, new BigDecimal( 100), LocalDateTime.of(2021,9,23, 3,12,15,156)),
//            new Order(2L, new BigDecimal( 100), LocalDateTime.of(2021,9,24, 3,12,15,156)));
//
//
//    @Autowired
//    private OrderDao orderDao;
//
//
//
//    @Test
//    public void findById() {
//        Optional<Order> expected = Optional.of(new Order(1L, new BigDecimal( 100),
//                LocalDateTime.of(2021,9,23, 3,12,15,156)));
//        Optional<Order> actual = orderDao.findById(1L);
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    public void findAll() {
//        List<Order> actual = orderDao.findAllOrders(1,1L);
//        assertEquals(EXPECTED_ORDER, actual);
//    }


//    @Test
//    public void deleteOrderTest() {
//        Optional<Order> createdOrder = orderDao.findById(1L);
//        orderDao.delete(createdOrder.get());
//        Optional<Order> emptyOrder = orderDao.findById(1L);
//        assertNotEquals(createdOrder, emptyOrder);
//    }
}