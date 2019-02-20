import axios from 'axios';
import {
  ICrudSearchAction,
  parseHeaderForLinks,
  loadMoreDataWhenScrolled,
  ICrudGetAction,
  ICrudGetAllAction,
  ICrudPutAction,
  ICrudDeleteAction
} from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IParaClassSdmSuffix, defaultValue } from 'app/shared/model/para-class-sdm-suffix.model';

export const ACTION_TYPES = {
  SEARCH_PARACLASSES: 'paraClass/SEARCH_PARACLASSES',
  FETCH_PARACLASS_LIST: 'paraClass/FETCH_PARACLASS_LIST',
  FETCH_PARACLASS: 'paraClass/FETCH_PARACLASS',
  CREATE_PARACLASS: 'paraClass/CREATE_PARACLASS',
  UPDATE_PARACLASS: 'paraClass/UPDATE_PARACLASS',
  DELETE_PARACLASS: 'paraClass/DELETE_PARACLASS',
  SET_BLOB: 'paraClass/SET_BLOB',
  RESET: 'paraClass/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IParaClassSdmSuffix>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type ParaClassSdmSuffixState = Readonly<typeof initialState>;

// Reducer

export default (state: ParaClassSdmSuffixState = initialState, action): ParaClassSdmSuffixState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_PARACLASSES):
    case REQUEST(ACTION_TYPES.FETCH_PARACLASS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PARACLASS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_PARACLASS):
    case REQUEST(ACTION_TYPES.UPDATE_PARACLASS):
    case REQUEST(ACTION_TYPES.DELETE_PARACLASS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_PARACLASSES):
    case FAILURE(ACTION_TYPES.FETCH_PARACLASS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PARACLASS):
    case FAILURE(ACTION_TYPES.CREATE_PARACLASS):
    case FAILURE(ACTION_TYPES.UPDATE_PARACLASS):
    case FAILURE(ACTION_TYPES.DELETE_PARACLASS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_PARACLASSES):
    case SUCCESS(ACTION_TYPES.FETCH_PARACLASS_LIST):
      const links = parseHeaderForLinks(action.payload.headers.link);
      return {
        ...state,
        links,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links)
      };
    case SUCCESS(ACTION_TYPES.FETCH_PARACLASS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_PARACLASS):
    case SUCCESS(ACTION_TYPES.UPDATE_PARACLASS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_PARACLASS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.SET_BLOB:
      const { name, data, contentType } = action.payload;
      return {
        ...state,
        entity: {
          ...state.entity,
          [name]: data,
          [name + 'ContentType']: contentType
        }
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/para-classes';
const apiSearchUrl = 'api/_search/para-classes';

// Actions

export const getSearchEntities: ICrudSearchAction<IParaClassSdmSuffix> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_PARACLASSES,
  payload: axios.get<IParaClassSdmSuffix>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`)
});

export const getEntities: ICrudGetAllAction<IParaClassSdmSuffix> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_PARACLASS_LIST,
    payload: axios.get<IParaClassSdmSuffix>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IParaClassSdmSuffix> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PARACLASS,
    payload: axios.get<IParaClassSdmSuffix>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IParaClassSdmSuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PARACLASS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<IParaClassSdmSuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PARACLASS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IParaClassSdmSuffix> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PARACLASS,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const setBlob = (name, data, contentType?) => ({
  type: ACTION_TYPES.SET_BLOB,
  payload: {
    name,
    data,
    contentType
  }
});

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
