var express = require('express');
var router = express.Router();
const p = require('../controllers/product.controller')
const authMiddleware = require('../middlewares/authMiddleware')

router.get('/getProduct', p.getProduct);
router.post('/addProduct', p.addProduct);
router.put('/editProduct/:productId', p.editProduct);
router.delete('/deleteProduct/:productId', p.deleteProduct);
router.get('/getProductDetail/:productId', p.getProductDetail);

router.get('/getCategory', p.getCategory);
router.post('/addCategory', p.addCategory);
router.put('/editCategory/:categoryId', p.editCategory);
router.delete('/deleteCategory/:categoryId', p.deleteCategory);

router.post('/addToCart', authMiddleware, p.addToCart);
router.get('/getCart', authMiddleware, p.getCart);
router.delete('/clearCart', authMiddleware, p.clearCart);

router.post('/checkout', authMiddleware, p.checkout);
router.get('/getOrder', authMiddleware, p.getOrder);

router.get('/search', p.search);


module.exports = router