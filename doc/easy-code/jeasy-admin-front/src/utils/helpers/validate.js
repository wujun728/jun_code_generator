export default {
  name: 'validate',

  validateInputNumber (rule, value, callback) {
    if (Number.isNaN(Number(value))) {
      callback(new Error(rule.message))
    } else {
      callback()
    }
  }
}
