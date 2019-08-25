/* eslint-disable import/prefer-default-export */
export const objectsToArray = objects =>
  Object.keys(objects).reduce((array, key) => {
    array.push(objects[key]);
    return array;
  }, []);
